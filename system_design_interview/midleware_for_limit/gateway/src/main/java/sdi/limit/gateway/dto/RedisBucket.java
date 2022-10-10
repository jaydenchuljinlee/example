package sdi.limit.gateway.dto;

import io.github.bucket4j.Bucket;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedisBucket {
    private String serverId;
    private Bucket bucket;
}
