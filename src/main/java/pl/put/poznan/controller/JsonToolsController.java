package pl.put.poznan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.service.JsonService;

import java.util.Arrays;


@RestController
@RequestMapping("/{text}")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);
    private final JsonService jsonService;

    public JsonToolsController(JsonService jsonService) {
        this.jsonService = jsonService;
    }


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(@PathVariable String text,
                              @RequestParam(value="transforms", defaultValue="upper,escape") String[] transforms) {

        logger.debug(text);
        logger.debug(Arrays.toString(transforms));

        return jsonService.transform(text);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public String post(@PathVariable String text,
                      @RequestBody String[] transforms) {

        logger.debug(text);
        logger.debug(Arrays.toString(transforms));

        return jsonService.transform(text);
    }



}


