package io.codegitters.sb_redis_demo_a.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisRecordRequest {
    
    private Map<String, Object> metadata;
    
    @NotNull
    @NotEmpty
    private Map<String, Object> data; 
}
