package sdi.limit.gateway.config.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class ApiUrlKeyResolver {
    @Bean
    KeyResolver apiUrlKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getURI().toString());
    }
}