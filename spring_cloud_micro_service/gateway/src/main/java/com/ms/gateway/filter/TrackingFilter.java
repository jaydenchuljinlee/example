package com.ms.gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class TrackingFilter extends AbstractGatewayFilterFactory<TrackingFilter.Config> {

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
            
            log.debug("Processing incomming request for {}", request.getPath());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

            }));
        });
    }

    public static class Config {}
}
