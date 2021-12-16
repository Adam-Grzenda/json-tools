package pl.put.poznan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.service.TransformerService;
import pl.put.poznan.transformer.TransformRequest;
import pl.put.poznan.service.JsonCompare;

import java.util.List;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class JsonToolsController {

    private final TransformerService transformerService;
    private final JsonCompare jsonCompare;

    @RequestMapping(value = "/minify", method = RequestMethod.POST, produces = "application/json")
    public String minify(@RequestBody String json,  @RequestParam(required = false) List<String> excludeFields,
                         @RequestParam(required = false) List<String> includeFields) throws JsonProcessingException {
        return transformerService.minify(TransformRequest.of(json, includeFields, excludeFields));
    }

    @RequestMapping(value = "/deminify", method = RequestMethod.POST, produces = "application/json")
    public String deminify(@RequestBody String json, @RequestParam(required = false) List<String> excludeFields,
                           @RequestParam(required = false) List<String> includeFields) throws JsonProcessingException {
        return transformerService.deminify(TransformRequest.of(json, excludeFields, includeFields));
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
    public String filter(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        request.setJson(json);
        return transformerService.filter(request);
    }

    @PostMapping(value = "/transform", produces = "application/json")
    public String transform(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        request.setJson(json);
        return transformerService.transform(request);
    }

    @RequestMapping(value = "/compare", method = RequestMethod.POST, produces = "application/json")
    public String compare(@RequestBody String text) throws IOException {
        log.debug(text);
        return jsonCompare.compare(text);
    }
}


