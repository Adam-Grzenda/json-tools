package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.JsonTransformerDecorator;
import pl.put.poznan.transformer.TransformRequest;

@Slf4j
public class MinifyTransformer extends JsonTransformerDecorator {

    public MinifyTransformer(JsonTransformer jsonTransformer) {
        super(jsonTransformer);
        log.info("Initialized MinifyTransformer");
    }

    @Override
    public TransformRequest transform(TransformRequest request) throws JsonProcessingException {
        return minify(super.transform(request));
    }

    private TransformRequest minify(TransformRequest request) throws JsonProcessingException {
        Object json = jsonMapper.readJson(request.getJson(), Object.class);
        String transformed = jsonMapper.writeJsonAsString(json, false);
        request.setJson(transformed);
        log.debug("Minified output: " + request);
        return request;
    }


}
