package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class JsonFilter {

    ObjectMapper inputMapper = new ObjectMapper();

    public String filter(String text, String[] fields) throws JsonProcessingException {
        fields = Arrays.stream(fields).filter(x -> !x.isEmpty()).toArray(String[]::new); //Remove empty strings
        String filters = String.join(",", fields);

        Object json = inputMapper.readValue(text, Object.class);
        ObjectMapper outputMapper = Squiggly.init(new ObjectMapper(), filters);
        return SquigglyUtils.stringify(outputMapper, json);
    }
}
