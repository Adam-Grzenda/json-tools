package pl.put.poznan.transformer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class TransformRequest {
    private boolean minified;
    private boolean deminified;

    private String[] excludeFields;
    private String[] includeFields;

    private String json;
}
