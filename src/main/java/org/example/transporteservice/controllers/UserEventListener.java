package org.example.transporteservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import jakarta.annotation.PostConstruct;
import org.example.transporteservice.models.Find;
import org.example.transporteservice.models.users.User;
import org.example.transporteservice.services.UserService;
import org.example.transporteservice.utils.JsonConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserEventListener {

    private final Connection natsConnection;
    @Autowired
    private UserService userService;

    @Autowired
    public UserEventListener(Connection natsConnection, UserService userService) {
        this.natsConnection = natsConnection;
        this.userService = userService;
    }

    @PostConstruct
    public void subscribeToUserEvents() {
        Dispatcher dispatcher = natsConnection.createDispatcher((msg) -> {
            String subject = msg.getSubject();
            String replyTo = msg.getReplyTo();
            String message = JsonConvert.extractData(msg.getData());
            System.out.println(message);

            String jsonResponse = switch (subject) {
                case "user.created" -> {
                    User createdDTO = parseMessage(message, User.class);
                    yield handleUserCreated(createdDTO);
                }
                case "user.updated" -> {
                    User updatedDTO = parseMessage(message, User.class);
                    yield handleUserUpdated(updatedDTO);
                }
                case "user.findall" -> handleFindAllUsers();

                case "user.findone" -> {
                    Find findObj = parseMessage(message, Find.class);
                    yield handleUserFindOne(findObj);
                }
                case "user.deleted" -> {
                    Find findObj = parseMessage(message, Find.class);
                    yield handleUserDelete(findObj);
                }
                default -> "";
            };

            if (replyTo != null) {
                System.out.println(jsonResponse);
                natsConnection.publish(replyTo, jsonResponse.getBytes());
            }
        });

        dispatcher.subscribe("user.created");
        dispatcher.subscribe("user.updated");
        dispatcher.subscribe("user.findall");
        dispatcher.subscribe("user.findone");
        dispatcher.subscribe("user.deleted");
    }

    private <T> T parseMessage(String message, Class<T> clazz) {
        return new Gson().fromJson(message, clazz);
    }

    private String handleUserCreated(User userDTO) {
        try {
            User createdUser = userService.createUser(userDTO);
            return this.handleFindAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al crear el usuario\"}";
        }
    }

    private String handleUserUpdated(User userDTO) {
        try {
            String id = userDTO.getId();
            User updatedUser = userService.updateUser(id, userDTO);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(updatedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al actualizar el usuario\"}";
        }
    }

    private String handleFindAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(users);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al obtener los usuarios\"}";
        }
    }

    private String handleUserFindOne(Find find) {
        List<User> list = new ArrayList<>();
        try {
            Optional<User> userOptional = userService.getUserById(find.getId());
            ObjectMapper objectMapper = new ObjectMapper();
            if (userOptional.isPresent()) {
                list.add(userOptional.get());
                return objectMapper.writeValueAsString(list);
            }
            return "{\"message\": \"Usuario no encontrado\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al buscar el usuario\"}";
        }
    }

    private String handleUserDelete(Find find) {
        try {
            userService.deleteUser(find.getId());
            return this.handleFindAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\": \"Error al eliminar el usuario\"}";
        }
    }
}
