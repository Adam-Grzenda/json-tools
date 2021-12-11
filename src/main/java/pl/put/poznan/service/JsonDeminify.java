package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonDeminify {
    public String deminify(String text){
        ObjectMapper mapper = new ObjectMapper();
        Object json = null;
        String deminified = null;
        try {
            json = mapper.readValue(text, Object.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            deminified = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return deminified;
    }
}
