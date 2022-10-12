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

import java.time.LocalDateTime;
import java.util.Collections;

@RequiredArgsConstructor
//@Component
public class ReactiveRedisListener {
    private final ReactiveRedisTemplate<String, RedisBucket> reactiveRedisTemplate;
    private final ReactiveRedisMessageListenerContainer reactiveRedisMessageListenerContainer;

    private final ChannelTopic channelTopic = new ChannelTopic("throttle-api");

    public Mono<Boolean> publish(RedisBucket bucket) {
        // TODO sortedSet의 key를 어떻게 지정할 것인가?
        // TODO Bucket을 레디스에 저장하는 시점에 즉, 여기에서 bucket의 count를 보고 판단해야 하는가, 아니면 단순히 필터에서만 검증할 것인가
        // TODO 필터에서만 검증하게 될 경우, 동시 접근 시 락을 거는 것이 아닌 순서대로 처리하기 때문에 데이터가 들어갈 수도??
        // TODO 예를 들면, 동시 접근 시 각각의 요청에서 count를 쓰는 작업보다 읽는 작업이 먼저 처리가 될 경우
        // TODO 만약 요청을 버린다면, 버린 요청에 대해 예외 처리를 통해서 처리량 제어를 해주는 것인지??

        return this.reactiveRedisTemplate.opsForZSet().add("throttle-api", bucket, LocalDateTime.now().getSecond());
    }

    public Flux<RedisBucket> subscribe() {
        return reactiveRedisMessageListenerContainer
                .receive(Collections.singletonList(channelTopic),
                        reactiveRedisTemplate.getSerializationContext().getKeySerializationPair(),
                        reactiveRedisTemplate.getSerializationContext().getValueSerializationPair())
                .map(ReactiveSubscription.Message::getMessage);

    }
}
