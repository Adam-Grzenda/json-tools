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
        return transform(request, null);
    }

    @Override
    public TransformRequest transform(TransformRequest request, JsonTransformer formatChecker) throws JsonProcessingException {
        return filter(request, formatChecker);
    }

    private TransformRequest filter(TransformRequest request, JsonTransformer formatChecker) throws JsonProcessingException {

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
                .toArray(String[]::new);

        if (includeFields.length > 0 && excludeFields.length > 0) {
            throw new IllegalArgumentException("Requesting fields to be both included and excluded is contradictory");
        }

        excludeFields = Optional.ofNullable(request.getExcludeFields())
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(x -> !x.isEmpty())
                .map(a -> "-" + a)
                .toArray(String[]::new);

        String filters = Stream.of(includeFields, excludeFields).flatMap(Stream::of).collect(Collectors.joining(","));

        if (!filters.isEmpty()) {
            if (formatChecker == null) {
                formatChecker = new FormatTransformer(new FilterTransformer());
            }
            boolean minifiedInput = ((FormatTransformer)formatChecker).isMinified(request);

            request.setJson(applyFilters(request.getJson(), filters));
            log.debug("Filtered output: " + request);

            if (!minifiedInput) {
                // Unwanted transformation done by the filtering library needs to be reverted
                TransformRequest deminifyRequest = new TransformRequest(false, true, request.getJson());
                request.setJson(((FormatTransformer)formatChecker).transform(deminifyRequest).getJson());
            }
        }

        return request;
    }

    private String applyFilters(String inputJson, String filters) throws JsonProcessingException {
        Object json = jsonMapper.readJson(inputJson, Object.class);
        // It's rather interesting that squiggly only pretty prints the object if it is literally Object,
        // whereas for JsonNode it doesn't even filter it

        ObjectMapper outputMapper = Squiggly.init(new ObjectMapper(), filters);
        log.debug("Applied filters: " + filters);
        return SquigglyUtils.stringify(outputMapper, json);
    }
}
