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

    /**
     * FilterTransformer object used for filtering JSON objects
     */
    private final FilterTransformer filterTransformer = new FilterTransformer();

    /**
     * Performs any implemented transformations on the json object
     *
     * @param data is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a transformed version of json
     */
    public String transform(TransformRequest data) throws JsonProcessingException {
        JsonTransformer transformer = new FormatTransformer(new SortTransformer(filterTransformer));

        log.debug("Transformer service output: " + data);
        return transformer.transform(data).getJson();
    }

    /**
     * Changes the format of the json object to minified or deminified (pretty printed)
     *
     * @param request is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a reformatted version of json
     */
    public String format(TransformRequest request) throws JsonProcessingException {
        JsonTransformer transformer = new FormatTransformer(filterTransformer);
        return transformer.transform(request).getJson();
    }

    /**
     * Sorts the json object's fields alphabetically
     *
     * @param request is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a sorted version of json
     */
    public String sort(TransformRequest request) throws JsonProcessingException {
        JsonTransformer transformer = new SortTransformer(filterTransformer);
        return transformer.transform(request).getJson();
    }

    /**
     * Simplifies the structure, leaving only the properties that the user wanted
     *
     * @param request is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a simplified version of json
     */
    public String filter(TransformRequest request) throws JsonProcessingException {
        return filter(request, null);
    }

    /**
     * Simplifies the structure, used in unit testing
     *
     * @param request is a string containing JSON object
     * @param formatChecker is a mock object
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a simplified version of json
     */
    public String filter(TransformRequest request, FormatTransformer formatChecker) throws JsonProcessingException {
        return filterTransformer.transform(request, formatChecker).getJson();
    }

}
