package io.codegitters.sb_redis_demo_a.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("record")
public class RedisRecord {
    
    @Id
    private UUID id;
    
    @Indexed
    private Long index;
    
    private Map<String, Object> metadata;
    private Map<String, Object> data;
    
    @TimeToLive
    private Long ttl;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime modifiedAt;
    
}
