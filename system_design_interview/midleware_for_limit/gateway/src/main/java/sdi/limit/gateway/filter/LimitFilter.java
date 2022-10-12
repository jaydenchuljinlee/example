package sdi.limit.gateway.filter;

import io.github.bucket4j.*;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import sdi.limit.gateway.dto.RedisBucket;
import sdi.limit.gateway.service.BucketRedisService;

import java.time.Duration;

@Deprecated
@RequiredArgsConstructor
@Component
public class LimitFilter extends AbstractGatewayFilterFactory<LimitFilter.Config> {
    private final BucketRedisService bucketRedisService;

    @Value("${server.name}")
    private String serverName;

    @Override
    public GatewayFilter apply(Config config) {
        Bucket bucket = bucketRedisService.getBucket(serverName).getBucket();

        if (bucket.tryConsume(1)) {
            return (exchange, chain) -> {
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                return exchange.getResponse().setComplete();
            };
        }

        RedisBucket newBucket = new RedisBucket(serverName, this.creatNewBucket());
        bucketRedisService.setBucket(newBucket);

        return (exchange, chain) -> {
            exchange.getResponse().setStatusCode(HttpStatus.CREATED);
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
