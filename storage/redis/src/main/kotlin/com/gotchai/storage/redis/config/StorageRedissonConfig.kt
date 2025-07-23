package com.gotchai.storage.redis.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StorageRedissonConfig(
    private val redisProperties: RedisProperties,
) {
    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config
            .useSingleServer()
            .setAddress("redis://${redisProperties.host}:${redisProperties.port}")
            .setConnectTimeout(3000)
            .setRetryAttempts(3)
        return Redisson.create(config)
    }
}
