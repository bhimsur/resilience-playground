package io.bhimsur.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CircuitBreakerConfiguration implements BeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(CircuitBreakerConfiguration.class);
    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    private TimeLimiterRegistry timeLimiterRegistry;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CircuitBreakerProperties) {
            CircuitBreakerProperties circuitBreakerProperties = (CircuitBreakerProperties) bean;
            circuitBreakerProperties.getInstances()
                    .values()
                    .stream()
                    .filter(p -> Objects.isNull(p.getBaseConfig()))
                    .forEach(p -> p.setBaseConfig("default"));
        }
        return bean;
    }

    @Bean
    public Resilience4JCircuitBreakerFactory resilience4JCircuitBreakerFactory() {
        Resilience4JCircuitBreakerFactory factory = new Resilience4JCircuitBreakerFactory(circuitBreakerRegistry, timeLimiterRegistry, null);
        factory.configureDefault(this::createConfiguration);
        return factory;
    }

    private Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration createConfiguration(String id) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(id);
        CircuitBreakerConfig circuitBreakerConfig = circuitBreaker.getCircuitBreakerConfig();
        TimeLimiterConfig timeLimiterConfig = timeLimiterRegistry.timeLimiter(id).getTimeLimiterConfig();
        circuitBreaker.getEventPublisher()
                .onEvent(event -> log.info("Circuit-Breaker Event Publisher : {}", event));
        return new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(circuitBreakerConfig)
                .timeLimiterConfig(timeLimiterConfig)
                .build();
    }
}
