package pl.put.poznan.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.put.poznan.transformer.mapper.JsonMapper;

public interface JsonTransformer {

    JsonMapper jsonMapper = new JsonMapper();

    TransformRequest transform(TransformRequest request) throws JsonProcessingException;
}
