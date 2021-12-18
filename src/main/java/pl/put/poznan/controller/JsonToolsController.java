package pl.put.poznan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.service.CompareService;
import pl.put.poznan.service.TransformerService;
import pl.put.poznan.transformer.TransformRequest;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class JsonToolsController {

    private final TransformerService transformerService;
    private final CompareService jsonCompare;

    @PostMapping(value = "/format", produces = "application/json")
    public String format(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        request.setJson(json);
        return transformerService.format(request);
    }

    @PostMapping(value = "/filter", produces = "application/json")
    public String filter(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        request.setJson(json);
        return transformerService.filter(request);
    }

    @PostMapping(value = "/transform", produces = "application/json")
    public String transform(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        request.setJson(json);
        return transformerService.transform(request);
    }

    @PostMapping(value = "/compare", produces = "application/json")
    public String compare(@RequestBody String text) throws IOException {
        log.debug(text);
        return jsonCompare.compare(text);
    }
}


