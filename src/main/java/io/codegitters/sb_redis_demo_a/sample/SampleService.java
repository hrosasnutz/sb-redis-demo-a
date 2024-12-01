package io.codegitters.sb_redis_demo_a.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codegitters.sb_redis_demo_a.api.request.RedisRecordRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleService {
    
    private final StringRedisTemplate template;
    
    private final ObjectMapper mapper;
    
    public Mono<UUID> save(RedisRecordRequest request) {
        var id = UUID.randomUUID();
        return Mono.fromCallable(() -> mapper.writeValueAsString(request))
                .flatMap(data -> Mono.fromRunnable(() -> template.opsForHash()
                        .put("sample_" + id, id.toString(), data)))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(rw -> log.debug("saved in redis has result: {}", rw))
                .doOnError(e -> log.error("Error on save in redis", e))
                .then(Mono.just(id));
    }
    
    public Mono<Void> setTtl(UUID id, Long ttl) {
        return Mono.fromRunnable(() -> template.expire("sample_" + id, ttl, TimeUnit.SECONDS))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(rw -> log.debug("set ttl in redis has result: {}", rw))
                .doOnError(e -> log.error("Error on set ttl in redis", e))
                .then();
    }
}
