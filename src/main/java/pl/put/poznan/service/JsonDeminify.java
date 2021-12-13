package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonDeminify {
    ObjectMapper mapper = new ObjectMapper();

    public String deminify(String text) throws JsonProcessingException{
        Object json = mapper.readValue(text, Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }
}
