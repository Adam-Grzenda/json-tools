package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.JsonTransformerDecorator;
import pl.put.poznan.transformer.TransformRequest;

/**
 * A class which contains a method to change the format of a JSON object to minified or deminified (pretty printed)
 */
@Slf4j
public class FormatTransformer extends JsonTransformerDecorator {

    /**
     * A constructor of the class, invokes JsonTransformerDecorator's constructor
     *
     * @param jsonTransformer instance of FilterTransformer class
     */
    public FormatTransformer(JsonTransformer jsonTransformer) {
        super(jsonTransformer);
        log.info("Initialized FormatTransformer");
    }

    /**
     * Invokes format method
     *
     * @param request instance of TransformRequest, contains JSON object, excluded and included fields
     * @return reformatted JSON object
     * @throws JsonProcessingException if JSON object cannot be reformatted
     */
    @Override
    public TransformRequest transform(TransformRequest request) throws JsonProcessingException {
        return format(super.transform(request));
    }

    /**
     * A method to format a JSON object
     *
     * @param request JSON object
     * @return reformatted JSON object
     * @throws JsonProcessingException if JSON object cannot be read or transformed
     */
    private TransformRequest format(TransformRequest request) throws JsonProcessingException {
        if (request.isDeminify() && request.isMinify()) {
            throw new IllegalArgumentException("Requesting both minify and deminify is contradictory");
        }
        else if (!request.isDeminify() && !request.isMinify()) {
            return request;
        }
        Object json = jsonMapper.readJson(request.getJson(), Object.class);
        String transformed = jsonMapper.writeJsonAsString(json, request.isDeminify()); // Equivalent to !request.isMinify()
        request.setJson(transformed);
        log.debug("Reformatted output: " + request);
        return request;
    }

}
