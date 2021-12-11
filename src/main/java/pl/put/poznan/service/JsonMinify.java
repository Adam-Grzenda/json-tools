package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonMinify {
    ObjectMapper mapper = new ObjectMapper();

    public String minify(String text){
        try {
            Object json;
            json = mapper.readValue(text, Object.class);
            return mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
