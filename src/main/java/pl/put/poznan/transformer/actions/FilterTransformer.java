package pl.put.poznan.transformer.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import lombok.extern.slf4j.Slf4j;
import pl.put.poznan.transformer.JsonTransformer;
import pl.put.poznan.transformer.TransformRequest;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FilterTransformer implements JsonTransformer {

    public FilterTransformer() {
        log.info("Initialized FilterTransformer");
    }

    @Override
    public TransformRequest transform(TransformRequest request) throws JsonProcessingException {
        return applyFilters(request);
    }

    private TransformRequest applyFilters(TransformRequest request) throws JsonProcessingException {

        if (request.getExcludeFields() == null && request.getIncludeFields() == null) {
            return request;
        }

        String[] includeFields = Optional.ofNullable(request.getIncludeFields())
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(x -> !x.isEmpty())
                .toArray(String[]::new);

        String[] excludeFields = Optional.ofNullable(request.getExcludeFields())
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(x -> !x.isEmpty())
                .map(a -> "-" + a)
                .toArray(String[]::new);

        String filters = Stream.of(includeFields, excludeFields).flatMap(Stream::of).collect(Collectors.joining(","));

        if (!filters.isEmpty()) {
            request.setJson(applyFilters(request.getJson(), filters));
            log.debug("Filtered output: " + request);
        }

        return request;
    }

    private String applyFilters(String inputJson, String filters) throws JsonProcessingException {
        Object json = jsonMapper.readJson(inputJson, Object.class);
        // It's rather interesting that squiggly only pretty prints the object if it is literally Object,
        // whereas for JsonNode it doesn't even filter it - investigate?

        // Given field "friends" and Applied filters: friends,-friends the field is in the response
        log.debug("Applied filters: " + filters);
        ObjectMapper outputMapper = Squiggly.init(new ObjectMapper(), filters);
        return SquigglyUtils.stringify(outputMapper, json);
    }
}
