package com.ms.gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TrackingFilter extends AbstractGatewayFilterFactory<TrackingFilter.Config> {
    public TrackingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            FilterUtils filterUtils = new FilterUtils(request, response);

            if (filterUtils.getCorrelationId() == null) {
                String correlationId = java.util.UUID.randomUUID().toString();
                filterUtils.setCorrelationId(correlationId);
            }

            log.debug("tmx-correlation-id: " + filterUtils.getCorrelationId() + ", " + request.getHeaders().get("tmx-correlation-id"));

            log.debug("Processing incoming request for {}", request.getPath());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

            }));
        });
    }

    public static class Config {}
}
