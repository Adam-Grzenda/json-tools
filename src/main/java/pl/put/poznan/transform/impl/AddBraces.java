package pl.put.poznan.transform.impl;

import pl.put.poznan.transform.JsonTransformer;
import pl.put.poznan.transform.JsonTransformerDecorator;

public class AddBraces extends JsonTransformerDecorator {
    public AddBraces(JsonTransformer jsonTransformer) {
        super(jsonTransformer);
    }

    @Override
    public String transform(String input) {
        return "(" + super.transform(input) + ")";
    }
}
