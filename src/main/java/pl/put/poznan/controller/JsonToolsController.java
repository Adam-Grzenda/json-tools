package pl.put.poznan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.service.CompareService;
import pl.put.poznan.service.TransformerService;
import pl.put.poznan.transformer.TransformRequest;

import java.io.IOException;
import java.util.List;

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
     * Minifies the json object
     *
     * @param json is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a minified version of json
     */
    @RequestMapping(value = "/minify", method = RequestMethod.POST, produces = "application/json")
    public String minify(@RequestBody String json, @RequestParam(required = false) List<String> excludeFields,
                         @RequestParam(required = false) List<String> includeFields) throws JsonProcessingException {
        log.info("Incoming request at endpoint /minify");
        return transformerService.minify(TransformRequest.of(json, excludeFields, includeFields));

    /**
     * Deminifies the json object
     *
     * @param json is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a deminified version of json
     */
    @RequestMapping(value = "/deminify", method = RequestMethod.POST, produces = "application/json")
    public String deminify(@RequestBody String json, @RequestParam(required = false) List<String> excludeFields,
                           @RequestParam(required = false) List<String> includeFields) throws JsonProcessingException {
        log.info("Incoming request at endpoint /deminify");
        return transformerService.deminify(TransformRequest.of(json, excludeFields, includeFields));
    }

    /**
     * Simplifies the structure, leaving only the properties that the user wanted
     *
     * @param json is a string containing the user's json
     * @throws JsonProcessingException is thrown if the operation on the mapper object fails
     * @return string containing a simplified version of json
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
    public String filter(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        log.info("Incoming request at endpoint /filter");
        request.setJson(json);
        return transformerService.filter(request);
    }

    @PostMapping(value = "/transform", produces = "application/json")
    public String transform(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        log.info("Incoming request at endpoint /transform");
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
    @RequestMapping(value = "/compare", method = RequestMethod.POST, produces = "application/json")
    public String compare(@RequestBody String text) throws IOException {
        log.info("Incoming request at endpoint /compare");
        return jsonCompare.compare(text);
    }
}


