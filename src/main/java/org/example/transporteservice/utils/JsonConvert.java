package org.example.transporteservice.utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonConvert {

    public static JsonObject convertStringToJson(String jsonString) {
        JsonParser parser = new JsonParser();
        return parser.parse(jsonString).getAsJsonObject();
    }

    public static String convertJsonToString(JsonObject jsonObject) {
        return jsonObject.toString();
    }

    public static <T> T convertJsonToObject(JsonObject jsonObject, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, clazz);
    }

    public static JsonObject convertObjectToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJsonTree(obj).getAsJsonObject();
    }

    public static String extractData(byte[] bytes) {
        try {
            // Convertir el byte[] a String
            String message = new String(bytes);

            // Crear el ObjectMapper de Jackson
            ObjectMapper objectMapper = new ObjectMapper();

            // Convertir el mensaje JSON en un JsonNode
            JsonNode rootNode = objectMapper.readTree(message);

            // Extraer el campo "data"
            JsonNode dataNode = rootNode.path("data");

            // Retornar el contenido de "data" como String, o un mensaje si no se encuentra
            if (!dataNode.isMissingNode()) {
                return dataNode.toString();  // Devuelve el contenido de "data"
            } else {
                return "No 'data' field found.";  // Si no se encuentra "data"
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing JSON: " + e.getMessage();
        }
    }

}
