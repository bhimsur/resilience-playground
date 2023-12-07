package io.bhimsur.controller;

import io.bhimsur.service.HttpBinService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/bin")
public class HttpBinController {
    private final HttpBinService httpBinService;

    public HttpBinController(HttpBinService httpBinService) {
        this.httpBinService = httpBinService;
    }

    @GetMapping(path = "/{seconds}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> delay(
            @PathVariable("seconds") int second
    ) {
        return httpBinService.delay(second);
    }

}
