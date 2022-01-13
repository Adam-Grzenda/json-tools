package pl.put.poznan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.service.CompareService;
import pl.put.poznan.service.TransformerService;
import pl.put.poznan.transformer.TransformRequest;

import java.io.IOException;

/**
 * Contains the associations of requests with their respective methods
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class JsonToolsController {

    private final TransformerService transformerService;
    private final CompareService jsonCompare;

    /**
     * Changes the format of the json object to minified or deminified (pretty printed)
     *
     * @param json is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a reformatted version of json
     */
    @PostMapping(value = "/format", produces = "application/json")
    public String format(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        log.info("Incoming request at endpoint /format");
        request.setJson(json);
        return transformerService.format(request);
    }

    /**
     * Sorts the json object's fields alphabetically
     *
     * @param json is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a sorted version of json
     */
    @PostMapping(value = "/sort", produces = "application/json")
    public String sort(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        log.info("Incoming request at endpoint /sort");
        request.setJson(json);
        return transformerService.sort(request);
    }

    /**
     * Simplifies the structure, leaving only the properties that the user wanted
     *
     * @param json is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a simplified version of json
     */
    @PostMapping(value = "/filter", produces = "application/json")
    public String filter(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        log.info("Incoming request at endpoint /filter");
        request.setJson(json);
        return transformerService.filter(request);
    }

    /**
     * Performs any implemented transformations on the json object
     *
     * @param json is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a transformed version of json
     */
    @PostMapping(value = "/transform", produces = "application/json")
    public String transform(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        request.setJson(json);
        return transformerService.transform(request);
    }

    /**
     * Comparison of two texts with an indication of the differences
     *
     * @param text is a string containing the user's json
     * @throws IOException is thrown if the operation on the mapper object fails
     * @return string with the line markings for the errors
     */
    @PostMapping(value = "/compare", produces = "text/plain")
    public String compare(@RequestBody String text) throws IOException {
        log.info("Incoming request at endpoint /compare");
        return jsonCompare.compare(text);
    }
}


