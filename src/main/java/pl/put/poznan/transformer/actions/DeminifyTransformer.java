package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.JsonTransformerDecorator;
import pl.put.poznan.transformer.TransformRequest;

/**
 * A class which contains a method to pretty-print a JSON object
 */
@Slf4j
public class DeminifyTransformer extends JsonTransformerDecorator {

    /**
     * A constructor of the class, invokes JsonTransformerDecorator's constructor
     *
     * @param jsonTransformer instance of FilterTransformer class
     */
    public DeminifyTransformer(JsonTransformer jsonTransformer) {
        super(jsonTransformer);
    }

    /**
     * Invokes deminify method
     *
     * @param request instance of TransformRequest, contains JSON object, excluded and included fields
     * @return pretty-printed JSON object
     * @throws JsonProcessingException if JSON object cannot be pretty-printed
     */
    @Override
    public TransformRequest transform(TransformRequest request) throws JsonProcessingException {
        return deminify(super.transform(request));
    }

    /**
     * A method to pretty-print a JSON object
     *
     * @param request A JSON object
     * @return pretty-printed JSON object
     * @throws JsonProcessingException if JSON object cannot be read or transformed
     */
    private TransformRequest deminify(TransformRequest request) throws JsonProcessingException {
        Object json = jsonMapper.readJson(request.getJson(), Object.class);
        String transformed = jsonMapper.writeJsonAsString(json, true);
        request.setJson(transformed);
        log.debug("Deminified output: " + request);
        return request;
    }

}
