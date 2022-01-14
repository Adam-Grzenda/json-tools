package pl.put.poznan.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.put.poznan.transformer.mapper.JsonMapper;

public interface JsonTransformer {

    JsonMapper jsonMapper = JsonMapper.getInstance();

    TransformRequest transform(TransformRequest request, JsonTransformer helper) throws JsonProcessingException;
}
