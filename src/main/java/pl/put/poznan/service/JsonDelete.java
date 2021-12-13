package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import org.springframework.stereotype.Service;

@Service
public class JsonDelete {
    ObjectMapper mapper = new ObjectMapper();

    public String delete(String text, String[] fields) throws JsonProcessingException {
            String filters = fields.length == 0 ? "" : "-" + String.join(",-", fields);

            Object json = mapper.readValue(text, Object.class);
            mapper = Squiggly.init(mapper, filters);
            return SquigglyUtils.stringify(mapper, json);
    }
}
