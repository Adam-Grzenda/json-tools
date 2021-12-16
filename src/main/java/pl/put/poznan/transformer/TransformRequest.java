package pl.put.poznan.transformer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TransformRequest {

    private boolean minify;
    private boolean deminify;

    private List<String> excludeFields;
    private List<String> includeFields;

    private String json;

    public static TransformRequest of(String json, List<String> excludeFields, List<String> includeFields) {
        TransformRequest transformRequest = new TransformRequest();
        transformRequest.setJson(json);
        transformRequest.setExcludeFields(excludeFields);
        transformRequest.setIncludeFields(includeFields);
        return transformRequest;
    }
}
