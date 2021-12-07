package pl.put.poznan.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.service.JsonService;

import java.util.Arrays;


@RestController
@RequestMapping("/api/{text}")
@RequiredArgsConstructor @Slf4j
public class JsonToolsController {

    private final JsonService jsonService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@PathVariable String text,
                              @RequestParam(value="transforms", defaultValue="upper,escape") String[] transforms) {

        log.debug(text);
        log.debug(Arrays.toString(transforms));

        return jsonService.addBracesAndRemoveWhitespace(text);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@PathVariable String text,
                      @RequestBody String transforms) {

        log.debug(text);
        return jsonService.addBracesAndRemoveWhitespace(transforms);
    }



}


