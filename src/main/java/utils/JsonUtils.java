package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtils {
    private JsonNode rootNode;

    public JsonUtils(String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            rootNode = objectMapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON file: " + filePath, e);
        }
    }

    public JsonNode getNode(String jsonPath) {
        return rootNode.at(jsonPath);
    }
}