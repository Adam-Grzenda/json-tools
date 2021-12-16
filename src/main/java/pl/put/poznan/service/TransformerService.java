package pl.put.poznan.service;

import org.springframework.stereotype.Service;
import pl.put.poznan.transformer.TransformRequest;

@Service
public class TransformerService {

    public String transform(TransformRequest data) {
        return data.getJson();
    }
}
