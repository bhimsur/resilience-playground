package io.bhimsur.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "io.bhimsur.feign.client")
public class FeignClientConfiguration {

}
