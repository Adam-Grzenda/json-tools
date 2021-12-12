package pl.put.poznan.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.service.JsonDeminify;
import pl.put.poznan.service.JsonMinify;
import pl.put.poznan.service.JsonFilter;

import java.util.Arrays;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor @Slf4j
public class JsonToolsController {

    private final JsonMinify jsonMinify;
    private final JsonDeminify jsonDeminify;
    private final JsonFilter jsonFilter;

    @RequestMapping(value = "/minify", method = RequestMethod.POST, produces = "application/json")
    public String minify(@RequestBody String text) {
        log.debug(text);

        return jsonMinify.minify(text);
    }

    @RequestMapping(value = "/deminify", method = RequestMethod.POST, produces = "application/json")
    public String deminify(@RequestBody String text) {
        log.debug(text);

        return jsonDeminify.deminify(text);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST, produces = "application/json")
    public String filter(@RequestBody String text, @RequestParam String[] fields) {
        log.debug(text);

        return jsonFilter.filter(text, fields);
    }

}


