package io.codegitters.sb_redis_demo_a.record;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.UUID;

public interface RedisRecordRepository extends KeyValueRepository<RedisRecord, UUID> {
}
