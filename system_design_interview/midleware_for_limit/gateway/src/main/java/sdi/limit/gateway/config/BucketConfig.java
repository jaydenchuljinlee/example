package sdi.limit.gateway.config;

import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class BucketConfig {

    ReactiveRedisOperations<String, Bucket> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Bucket> serializer = new Jackson2JsonRedisSerializer<Bucket>(Bucket.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Bucket> builder = RedisSerializationContext.newSerializationContext(
                new StringRedisSerializer());

        RedisSerializationContext<String, Bucket> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
