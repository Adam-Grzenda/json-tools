package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.JsonTransformerDecorator;
import pl.put.poznan.transformer.TransformRequest;

/**
 * A class which contains a method to minify a JSON object
 */
@Slf4j
public class MinifyTransformer extends JsonTransformerDecorator {

    /**
     * A constructor of the class, invokes JsonTransformerDecorator's constructor
     *
     * @param jsonTransformer instance of FilterTransformer class
     */
    public MinifyTransformer(JsonTransformer jsonTransformer) {
        super(jsonTransformer);
        log.info("Initialized MinifyTransformer");
    }

    /**
     * Invokes minify method
     *
     * @param request instance of TransformRequest, contains JSON object, excluded and included fields
     * @return minified JSON object
     * @throws JsonProcessingException if JSON object cannot be minified
     */
    @Override
    public TransformRequest transform(TransformRequest request) throws JsonProcessingException {
        return minify(super.transform(request));
    }

    /**
     * A method to minify a JSON object
     *
     * @param request JSON object
     * @return minified JSON object
     * @throws JsonProcessingException if JSON object cannot be read or transformed
     */
    private TransformRequest minify(TransformRequest request) throws JsonProcessingException {
        Object json = jsonMapper.readJson(request.getJson(), Object.class);
        String transformed = jsonMapper.writeJsonAsString(json, false);
        request.setJson(transformed);
        log.debug("Minified output: " + request);
        return request;
    }


}
