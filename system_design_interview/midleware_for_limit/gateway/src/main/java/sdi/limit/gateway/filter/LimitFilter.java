package sdi.limit.gateway.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.cache.Cache;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Supplier;

@Component
public class LimitFilter extends AbstractGatewayFilterFactory<LimitFilter.Config> {

    private final Cache<String, >

    private ProxyManager<String> buckets;

    public LimitFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        BucketConfiguration bucketConfiguration = BucketConfiguration.builder().addLimit(Bandwidth.simple(10, Duration.ofMillis(1))).build();

        Supplier<BucketConfiguration> configurationLazySupplier = () -> bucketConfiguration;

        Bucket bucket =

        return (exchange, chain) -> {

        };
    }

    public static class Config {}
}
