package io.bhimsur.feign.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HttpBinClientImpl implements HttpBinClient {
    private static final Logger log = LoggerFactory.getLogger(HttpBinClientImpl.class);
    @Override
    public Map<String, Object> delayInSeconds(int delay) {
        log.info("Return from fallback delayInSeconds");
        return new HashMap<>();
    }
}
