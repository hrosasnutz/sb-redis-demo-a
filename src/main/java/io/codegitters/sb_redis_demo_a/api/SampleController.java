package io.codegitters.sb_redis_demo_a.api;

import io.codegitters.sb_redis_demo_a.api.request.RedisRecordRequest;
import io.codegitters.sb_redis_demo_a.sample.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/api/samples")
@RequiredArgsConstructor
public class SampleController {
    
    private final SampleService service;

    @PostMapping
    Mono<UUID> create(@RequestBody @Validated RedisRecordRequest request) {
        return service.save(request);
    }

    @PatchMapping("/{id}/ttl/{ttl}")
    Mono<Void> setTtl(@PathVariable UUID id, @PathVariable Long ttl) {
        return service.setTtl(id, ttl);
    }
}
