package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.JsonTransformerDecorator;
import pl.put.poznan.transformer.TransformRequest;

@Slf4j
public class DeminifyTransformer extends JsonTransformerDecorator {

    public DeminifyTransformer(JsonTransformer jsonTransformer) {
        super(jsonTransformer);
    }

    @Override
    public TransformRequest transform(TransformRequest request) throws JsonProcessingException {
        return deminify(super.transform(request));
    }

    private TransformRequest deminify(TransformRequest request) throws JsonProcessingException {
        Object json = jsonMapper.readJson(request.getJson());
        String transformed = jsonMapper.writeJsonAsString(json, true);
        request.setJson(transformed);
        log.debug("Deminified output: " + request);
        return request;
    }

}
