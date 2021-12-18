package pl.put.poznan.transformer.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    private JsonMapper() {
    }

    private static JsonMapper jsonMapper = null;

    public static JsonMapper getInstance() {
        if (jsonMapper == null) {
            jsonMapper = new JsonMapper();
        }
        return jsonMapper;
    }

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T readJson(String input, Class<T> type) throws JsonProcessingException {
        return mapper.readValue(input, type);
    }

    public String writeJsonAsString(Object json, boolean prettyPrint) throws JsonProcessingException {
        if (prettyPrint) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } else {
            return mapper.writeValueAsString(json);
        }
    }

}
