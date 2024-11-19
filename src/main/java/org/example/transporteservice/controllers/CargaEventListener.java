package org.example.transporteservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import jakarta.annotation.PostConstruct;
import org.example.transporteservice.models.Find;
import org.example.transporteservice.models.cargas.Carga;
import org.example.transporteservice.services.CargaService;
import org.example.transporteservice.utils.JsonConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CargaEventListener {

    private final Connection natsConnection;
    @Autowired
    private final CargaService cargaService;

    @Autowired
    public CargaEventListener(Connection natsConnection, CargaService cargaService) {
        this.natsConnection = natsConnection;
        this.cargaService = cargaService;
    }

    @PostConstruct
    public void subscribeToCargaEvents() {
        Dispatcher dispatcher = natsConnection.createDispatcher((msg) -> {
            String subject = msg.getSubject();
            String replyTo = msg.getReplyTo();
            byte[] bytes = msg.getData();
            String message = JsonConvert.extractData(bytes);
            System.out.println(message);

            String jsonResponse = switch (subject) {
                case "carga.created" -> {
                    Carga createdCarga = parseMessage(message, Carga.class);
                    yield handleCargaCreated(createdCarga);
                }
                case "carga.updated" -> {
                    Carga updatedCarga = parseMessage(message, Carga.class);
                    yield handleCargaUpdated(updatedCarga);
                }
                case "carga.findall" -> handleFindAllCargas();

                case "carga.findone" -> {
                    Find find = parseMessage(message, Find.class);
                    yield handleCargaFindOne(find);
                }
                case "carga.deleted" -> {
                    Find find = parseMessage(message, Find.class);
                    yield handleCargaDelete(find);
                }
                default -> "";
            };

            if (replyTo != null) {
                System.out.println(jsonResponse);
                natsConnection.publish(replyTo, jsonResponse.getBytes());
            }
        });

        dispatcher.subscribe("carga.created");
        dispatcher.subscribe("carga.updated");
        dispatcher.subscribe("carga.findall");
        dispatcher.subscribe("carga.findone");
        dispatcher.subscribe("carga.deleted");
    }

    private <T> T parseMessage(String message, Class<T> clazz) {
        return new Gson().fromJson(message, clazz);
    }

    private String handleCargaCreated(Carga carga) {
        try {
            Carga createdCarga = cargaService.create(carga);
            return this.handleFindAllCargas();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al crear la carga\"}";
        }
    }

    private String handleCargaUpdated(Carga carga) {
        try {
            String id = carga.getId();
            Carga updatedCarga = cargaService.update(id, carga);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(updatedCarga);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al actualizar la carga\"}";
        }
    }

    private String handleFindAllCargas() {
        try {
            List<Carga> cargas = cargaService.findAll();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(cargas);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al obtener las cargas\"}";
        }
    }

    private String handleCargaFindOne(Find find) {
        List<Carga> list = new ArrayList<>();
        try {
            Optional<Carga> cargaOptional = cargaService.findById(find.getId());
            ObjectMapper objectMapper = new ObjectMapper();
            if (cargaOptional.isPresent()) {
                list.add(cargaOptional.get());
                return objectMapper.writeValueAsString(list);
            } else {
                return "{\"message\": \"Carga no encontrada\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al buscar la carga\"}";
        }
    }

    private String handleCargaDelete(Find find) {
        try {
           cargaService.deleteById(find.getId());
           return this.handleFindAllCargas();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al buscar la carga\"}";
        }
    }
}
