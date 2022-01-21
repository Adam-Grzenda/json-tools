package pl.put.poznan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.TransformRequest;
import pl.put.poznan.transformer.actions.FormatTransformer;
import pl.put.poznan.transformer.actions.FilterTransformer;
import pl.put.poznan.transformer.actions.SortTransformer;

/**
 * A class that invokes operations performed on json documents
 */
@Service
@Slf4j
public class TransformerService {

    private final FilterTransformer filterTransformer = new FilterTransformer();

    public String transform(TransformRequest data) throws JsonProcessingException {
        JsonTransformer transformer = new FormatTransformer(new SortTransformer(filterTransformer));

        log.debug("Transformer service output: " + data);
        return transformer.transform(data).getJson();
    }

    public String format(TransformRequest request) throws JsonProcessingException {
        JsonTransformer transformer = new FormatTransformer(filterTransformer);
        return transformer.transform(request).getJson();
    }

    public String sort(TransformRequest request) throws JsonProcessingException {
        JsonTransformer transformer = new SortTransformer(filterTransformer);
        return transformer.transform(request).getJson();
    }

    public String filter(TransformRequest request) throws JsonProcessingException {
        return filter(request, null);
    }

    public String filter(TransformRequest request, FormatTransformer formatChecker) throws JsonProcessingException {
        return filterTransformer.transform(request, formatChecker).getJson();
    }

}
