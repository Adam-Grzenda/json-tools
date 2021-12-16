package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public Object readJson(String input) throws JsonProcessingException {
        return mapper.readValue(input, Object.class);
    }

    public String writeJsonAsString(Object json, boolean prettyPrint) throws JsonProcessingException {
        if (prettyPrint) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } else {
            return mapper.writeValueAsString(json);
        }
    }

}
