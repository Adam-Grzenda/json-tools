package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.JsonTransformerDecorator;
import pl.put.poznan.transformer.TransformRequest;

import java.util.HashMap;

/**
 * A class which contains a method to sort a JSON object by a keys
 */
@Slf4j
public class SortTransformer extends JsonTransformerDecorator {
    /**
     * A constructor of the class, invokes JsonTransformerDecorator's constructor
     *
     * @param jsonTransformer instance of FilterTransformer class
     */
    public SortTransformer(JsonTransformer jsonTransformer) {
        super(jsonTransformer);
        log.info("Initialized SortTransformer");
    }

    /**
     * Invokes sort method
     *
     * @param request instance of TransformRequest, contains JSON object and requested transformations
     * @return sorted JSON object
     * @throws JsonProcessingException if JSON object cannot be sorted
     */
    @Override
    public TransformRequest transform(TransformRequest request, JsonTransformer helper) throws JsonProcessingException {
        return sort(super.transform(request, helper));
    }

    /**
     * A method to sort a JSON object by a key
     *
     * @param request instance of TransformRequest, contains JSON object and requested transformations
     * @return sorted JSON object
     * @throws JsonProcessingException if JSON object cannot be read or transformed
     */
    private TransformRequest sort(TransformRequest request) throws JsonProcessingException {
        FormatTransformer formatChecker = new FormatTransformer(new FilterTransformer());
        boolean minifiedInput = formatChecker.isMinified(request);

        Object json = jsonMapper.readJson(request.getJson(), Object.class);
        String transformed = jsonMapper.writeJsonAsString(json, true);
        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        request.setJson(om.writeValueAsString(om.readValue(transformed, HashMap.class)));
        log.debug("Sorted output: " + request);

        if (!minifiedInput) {
            // Unwanted transformation done by the sorting library needs to be reverted
            TransformRequest deminifyRequest = new TransformRequest(false, true, request.getJson());
            request.setJson(formatChecker.transform(deminifyRequest).getJson());
        }

        return request;
    }
}
