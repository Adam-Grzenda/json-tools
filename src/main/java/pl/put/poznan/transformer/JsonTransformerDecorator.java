package pl.put.poznan.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class JsonTransformerDecorator implements JsonTransformer {

    private final JsonTransformer jsonTransformer;

    @Override
    public TransformRequest transform(TransformRequest request, JsonTransformer helper) throws JsonProcessingException {
        return jsonTransformer.transform(request, helper);
    }
}
