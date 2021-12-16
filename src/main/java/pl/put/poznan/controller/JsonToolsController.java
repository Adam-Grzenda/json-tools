package pl.put.poznan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.service.*;
import pl.put.poznan.transformer.TransformRequest;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class JsonToolsController {

    private final JsonMinify jsonMinify;
    private final JsonDeminify jsonDeminify;
    private final JsonFilter jsonFilter;
    private final JsonDelete jsonDelete;

    private final TransformerService transformerService;


    @RequestMapping(value = "/minify", method = RequestMethod.POST, produces = "application/json")
    public String minify(@RequestBody String text) throws JsonProcessingException {
        log.debug(text);
        return jsonMinify.minify(text);
    }

    @RequestMapping(value = "/deminify", method = RequestMethod.POST, produces = "application/json")
    public String deminify(@RequestBody String text) throws JsonProcessingException {
        log.debug(text);
        return jsonDeminify.deminify(text);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
    public String filter(@RequestBody String text, @RequestParam String[] fields) throws JsonProcessingException {
        log.debug(text);
        return jsonFilter.filter(text, fields);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public String delete(@RequestBody String text, @RequestParam String[] fields) throws JsonProcessingException {
        log.debug(text);
        return jsonDelete.delete(text, fields);
    }

    @PostMapping(value = "/transform")
    public String transform(@RequestBody String json, TransformRequest request) throws JsonProcessingException {
        request.setJson(json);
        return transformerService.transform(request);
    }


}


