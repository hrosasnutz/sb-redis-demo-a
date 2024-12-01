package io.codegitters.sb_redis_demo_a.api;

import io.codegitters.sb_redis_demo_a.api.request.RedisRecordRequest;
import io.codegitters.sb_redis_demo_a.record.RedisRecord;
import io.codegitters.sb_redis_demo_a.record.RedisRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RedisRecordController {
    
    private final RedisRecordRepository repository;
    
    @PostMapping
    Mono<UUID> create(@RequestBody @Validated RedisRecordRequest request) {
       return Mono.just(request)
               .map(r -> RedisRecord.builder()
                       .id(UUID.randomUUID())
                       .metadata(request.getMetadata())
                       .data(request.getData())
                       .ttl(-1L)
                       .build())
               .flatMap(r -> Mono.fromCallable(() -> repository.save(r))
                       .subscribeOn(Schedulers.boundedElastic()))
               .map(RedisRecord::getId);
    }
    
    @PatchMapping("/{id}/ttl/{ttl}")
    Mono<Void> setTtl(@PathVariable UUID id, @PathVariable Long ttl) {
        return Mono.fromCallable(() -> repository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Mono::justOrEmpty)
                .map(r -> r.withTtl(ttl))
                .flatMap(r -> Mono.fromCallable(() -> repository.save(r))
                        .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }
}
