package org.example.transporteservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import jakarta.annotation.PostConstruct;
import org.example.transporteservice.models.Find;
import org.example.transporteservice.models.vehiculos.Vehiculo;
import org.example.transporteservice.services.VehiculoService;
import org.example.transporteservice.utils.JsonConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoEventListener {

    private final Connection natsConnection;
    @Autowired
    private VehiculoService vehiculoService;


    @Autowired
    public VehiculoEventListener(Connection natsConnection, VehiculoService vehiculoService) {
        this.natsConnection = natsConnection;
        this.vehiculoService = vehiculoService;
    }

    @PostConstruct
    public void subscribeToVehiculoEvents() {
        Dispatcher dispatcher = natsConnection.createDispatcher((msg) -> {
            String subject = msg.getSubject();
            String replyTo = msg.getReplyTo();
            byte[] bytes = msg.getData();
            String message = JsonConvert.extractData(bytes);
            System.out.println(message);
            String jsonResponse = switch (subject) {
                case "vehiculo.created" -> {
                    Vehiculo createdDTO = parseMessage(message, Vehiculo.class);
                    yield handleVehiculoCreated(createdDTO);
                }
                case "vehiculo.updated" -> {
                    Vehiculo updatedDTO = parseMessage(message, Vehiculo.class);
                    yield handleVehiculoUpdated(updatedDTO);
                }
                case "vehiculo.findall" -> handleFindAllVehiculos();

                case "vehiculo.findone" -> {
                    Find obj = parseMessage(message, Find.class);
                    yield handleVehiculoFindOne(obj);
                }
                case "vehiculo.deleted" -> {
                    Find obj = parseMessage(message, Find.class);
                    yield handleVehiculoDelete(obj);
                }
                default -> "";
            };

            if (replyTo != null) {
                System.out.println(jsonResponse);
                System.out.println(replyTo);
                natsConnection.publish(replyTo, jsonResponse.getBytes());
                System.out.println(replyTo);
            }
        });

        dispatcher.subscribe("vehiculo.created");
        dispatcher.subscribe("vehiculo.updated");
        dispatcher.subscribe("vehiculo.findall");
        dispatcher.subscribe("vehiculo.findone");
        dispatcher.subscribe("vehiculo.deleted");
    }

    private <T> T parseMessage(String message, Class<T> clazz) {
        return new Gson().fromJson(message, clazz); // Use Gson or another JSON parser
    }

    private String handleVehiculoCreated(Vehiculo vehiculoDTO) {
        try {
        Vehiculo creado = vehiculoService.crearVehiculo(vehiculoDTO);
        return  this.handleFindAllVehiculos();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al convertir el objeto a JSON\"}";
        }
    }

    private String handleVehiculoUpdated(Vehiculo vehiculoDTO) {
        try {
            String id = vehiculoDTO.getId();
            Vehiculo updated = vehiculoService.actualizarVehiculo(id, vehiculoDTO);

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al actualizar el vehículo\"}";
        }
    }


    private String handleFindAllVehiculos() {
        try {
            List<Vehiculo> vehiculos = vehiculoService.obtenerTodosLosVehiculos();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(vehiculos);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al obtener los vehículos\"}";
        }
    }

    private String handleVehiculoFindOne(Find find) {
        List<Vehiculo> list = new ArrayList<>();
        try {
            Optional<Vehiculo> updated = vehiculoService.obtenerVehiculoPorId(find.getId());
            Vehiculo vehiculo;
            ObjectMapper objectMapper = new ObjectMapper();
            if( updated.isPresent()) {
                vehiculo = updated.get();
                list.add(vehiculo);
            return objectMapper.writeValueAsString(list);
            }
            return "{\"message\": \"Error al actualizar el vehículo\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al actualizar el vehículo\"}";
        }
    }
    private String handleVehiculoDelete(Find find) {
        try {
            vehiculoService.eliminarVehiculo(find.getId());
            return this.handleFindAllVehiculos();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al eliminar el vehículo\"}";
        }
    }

}
