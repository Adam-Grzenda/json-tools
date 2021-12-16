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
}
