package sdi.limit.gateway.service;

import sdi.limit.gateway.dto.RedisBucket;

public interface BucketRedisService {
    RedisBucket getBucket(String serverId);
    void setBucket(RedisBucket bucket);
}
