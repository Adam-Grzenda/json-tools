package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import org.springframework.stereotype.Service;

@Service
public class JsonFilter {
    ObjectMapper mapper = new ObjectMapper();

    public String filter(String text, String[] fields) {
        try {
            String filters = String.join(",", fields);

            Object json = mapper.readValue(text, Object.class);
            mapper = Squiggly.init(new ObjectMapper(), filters);
            return SquigglyUtils.stringify(mapper, json);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
