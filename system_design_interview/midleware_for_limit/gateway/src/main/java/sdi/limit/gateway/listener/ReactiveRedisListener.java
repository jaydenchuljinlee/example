package sdi.limit.gateway.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sdi.limit.gateway.dto.RedisBucket;

import java.util.Collections;

@RequiredArgsConstructor
@Component
public class ReactiveRedisListener {
    private final ReactiveRedisTemplate<String, RedisBucket> reactiveRedisTemplate;
    private final ReactiveRedisMessageListenerContainer reactiveRedisMessageListenerContainer;

    private final ChannelTopic channelTopic = new ChannelTopic("throttle-api");

    public Mono<Void> publish(RedisBucket bucket) {
        return this.reactiveRedisTemplate
                .convertAndSend(channelTopic.getTopic(), bucket)
                .then(Mono.empty());
    }

    public Flux<RedisBucket> subscribe() {
        return reactiveRedisMessageListenerContainer
                .receive(Collections.singletonList(channelTopic),
                        reactiveRedisTemplate.getSerializationContext().getKeySerializationPair(),
                        reactiveRedisTemplate.getSerializationContext().getValueSerializationPair())
                .map(ReactiveSubscription.Message::getMessage);

    }
}
