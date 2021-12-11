package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonDeminify {
    ObjectMapper mapper = new ObjectMapper();

    public String deminify(String text){
        Object json;
        String deminified;

        try {
            json = mapper.readValue(text, Object.class);
            deminified = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            return deminified;
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
