package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.TransformRequest;
import pl.put.poznan.transformer.actions.DeminifyTransformer;
import pl.put.poznan.transformer.actions.FilterTransformer;
import pl.put.poznan.transformer.actions.MinifyTransformer;

@Service
@Slf4j
public class TransformerService {

    public String transform(TransformRequest data) throws JsonProcessingException {
        if (data.isDeminify() && data.isMinify()) {
            throw new IllegalArgumentException("Requesting both minify and deminify is contradictory");
        }

        JsonTransformer transformer = new DeminifyTransformer(new FilterTransformer());

        if (data.isMinify()) {
            transformer = new MinifyTransformer(transformer);
        }

        log.debug("Transformer service output: " + data);
        return transformer.transform(data).getJson();
    }
}
