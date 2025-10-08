package utils;

import com.fasterxml.jackson.databind.JsonNode;

public class TestDataUtils {
    private JsonUtils jsonUtils;

    public TestDataUtils() {
        this.jsonUtils = new JsonUtils("src/main/resources/testdata.json");
    }

    public String getName(int index) {
        return jsonUtils.getNode("/users/" + index + "/name").asText();
    }

    public String getEmail(int index) {
        return jsonUtils.getNode("/users/" + index + "/email").asText();
    }

    public String getAddress(int index) {
        return jsonUtils.getNode("/users/" + index + "/address").asText();
    }
}