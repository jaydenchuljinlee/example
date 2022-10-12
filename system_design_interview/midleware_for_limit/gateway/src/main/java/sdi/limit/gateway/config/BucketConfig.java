package sdi.limit.gateway.config;

import io.github.bucket4j.Bucket;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import sdi.limit.gateway.properties.RedisBucketProperty;

@Configuration
public class BucketConfig {
    @Autowired
    private RedisBucketProperty property;

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory(property.getHost(), property.getPort());
    }

    @Bean
    ReactiveRedisOperations<String, Bucket> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Bucket> serializer = new Jackson2JsonRedisSerializer<Bucket>(Bucket.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Bucket> builder = RedisSerializationContext.newSerializationContext(
                new StringRedisSerializer());

        RedisSerializationContext<String, Bucket> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    ReactiveRedisMessageListenerContainer container(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisMessageListenerContainer(factory);
    }
}
