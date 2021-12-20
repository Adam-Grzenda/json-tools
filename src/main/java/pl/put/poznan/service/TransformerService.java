package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.TransformRequest;
import pl.put.poznan.transformer.actions.DeminifyTransformer;
import pl.put.poznan.transformer.actions.FilterTransformer;
import pl.put.poznan.transformer.actions.MinifyTransformer;

import java.io.IOException;

/**
 * A class that implements operations performed on json documents
 */
@Service
@Slf4j
public class TransformerService {

    private final FilterTransformer filterTransformer = new FilterTransformer();

    public String transform(TransformRequest data) throws JsonProcessingException {
        if (data.isDeminify() && data.isMinify()) {
            throw new IllegalArgumentException("Requesting both minify and deminify is contradictory");
        }

        JsonTransformer transformer;

        if (data.isMinify()) {
            transformer = new MinifyTransformer(filterTransformer);
        }
        else {
            transformer = new DeminifyTransformer(filterTransformer);
        }

        log.debug("Transformer service output: " + data);
        return transformer.transform(data).getJson();
    }

    public String minify(TransformRequest request) throws JsonProcessingException {
        JsonTransformer transformer = new MinifyTransformer(filterTransformer);
        return transformer.transform(request).getJson();
    }

    public String deminify(TransformRequest request) throws JsonProcessingException {
        JsonTransformer transformer = new DeminifyTransformer(filterTransformer);
        return transformer.transform(request).getJson();
    }

    public String filter(TransformRequest request) throws JsonProcessingException {
        return filterTransformer.transform(request).getJson();
    }


}
