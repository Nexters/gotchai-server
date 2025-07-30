package com.gotchai.storage.redis.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories(basePackages = ["com.gotchai.storage.redis"])
class RedisConfig
