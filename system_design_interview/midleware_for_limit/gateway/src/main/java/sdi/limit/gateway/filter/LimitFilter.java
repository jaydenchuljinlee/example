package sdi.limit.gateway.filter;

import io.github.bucket4j.*;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.cache.Cache;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class LimitFilter extends AbstractGatewayFilterFactory<LimitFilter.Config> {

    private ProxyManager<String> buckets;

    public LimitFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        //Bucket bucket =

        return (exchange, chain) -> {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        };
    }

    private Bucket creatNewBucket() {
        long overdraft = 50;
        Refill refill = Refill.greedy(10, Duration.ofSeconds(1));
        Bandwidth limit = Bandwidth.classic(overdraft, refill);
        return Bucket.builder().addLimit(limit).build();
    }

    public static class Config {}
}
