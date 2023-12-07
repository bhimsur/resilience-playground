package io.bhimsur.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
@FeignClient(name = "httpBin",
        url = "https://httpbin.org",
        fallback = HttpBinClientImpl.class)
public interface HttpBinClient {

    @GetMapping(path = "/delay/{inSeconds}", produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, Object> delayInSeconds(@PathVariable("inSeconds") int delay);
}
