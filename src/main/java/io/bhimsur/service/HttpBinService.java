package io.bhimsur.service;

import io.bhimsur.feign.client.HttpBinClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HttpBinService {
    @Autowired
    private HttpBinClient httpBinClient;

    public Map<String, Object> delay(int inSeconds) {
        try {
            return httpBinClient.delayInSeconds(inSeconds);
        } catch (Exception e) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("error", e.getMessage());
            return map;
        }
    }
}
