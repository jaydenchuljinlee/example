package sdi.limit.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sdi.limit.gateway.dto.RedisBucket;
import sdi.limit.gateway.listener.ReactiveRedisListener;

@RequiredArgsConstructor
@Service
public class BucketRedisServiceImpl implements BucketRedisService{
    private ReactiveRedisListener listener;

    @Override
    public RedisBucket getBucket(String serverId) {
        return listener.subscribe().last().block();
    }

    @Override
    public void setBucket(RedisBucket bucket) {
        listener.publish(bucket);
    }
}
