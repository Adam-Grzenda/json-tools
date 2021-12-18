package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.JsonTransformerDecorator;
import pl.put.poznan.transformer.TransformRequest;

@Slf4j
public class FormatTransformer extends JsonTransformerDecorator {

    public FormatTransformer(JsonTransformer jsonTransformer) {
        super(jsonTransformer);
    }

    @Override
    public TransformRequest transform(TransformRequest request) throws JsonProcessingException {
        return format(super.transform(request));
    }

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
