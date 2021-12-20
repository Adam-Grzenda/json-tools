package pl.put.poznan.transformer.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonMapper {

    private JsonMapper() {
        log.info("Initialized JsonMapper");
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
