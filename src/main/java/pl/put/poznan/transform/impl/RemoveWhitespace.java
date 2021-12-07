package pl.put.poznan.transform.impl;

import pl.put.poznan.transform.JsonTransformer;
import pl.put.poznan.transform.JsonTransformerDecorator;

public class RemoveWhitespace extends JsonTransformerDecorator {
    public RemoveWhitespace(JsonTransformer jsonTransformer) {
        super(jsonTransformer);
    }

    @Override
    public String transform(String input) {
        return removeWhitespace(super.transform(input));
    }

    private String removeWhitespace(String input) {
        return input.replaceAll("\\s+", "");
    }

}
