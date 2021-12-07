package pl.put.poznan.transform;

public abstract class JsonTransformerDecorator implements JsonTransformer {
    private final JsonTransformer jsonTransformer;

    public JsonTransformerDecorator(JsonTransformer jsonTransformer) {
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String transform(String input) {
        return jsonTransformer.transform(input);
    }
}
