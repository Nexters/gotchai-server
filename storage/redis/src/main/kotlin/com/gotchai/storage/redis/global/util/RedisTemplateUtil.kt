package com.gotchai.storage.redis.global.util

import com.gotchai.domain.global.exception.NotFoundDataException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import java.time.Duration

fun <T : Any> RedisTemplate<String, T>.setWithExpiry(key: String, value: T, duration: Duration) {
    opsForValue().set(key, value, duration)
}
fun <T> RedisTemplate<String, T>.getOrElseThrow(key: String): T {
    return opsForValue().get(key) ?: throw NotFoundDataException()
}

fun <T> RedisTemplate<String, T>.getOrDefault(key: String, defaultValue: T): T {
    return opsForValue().get(key) ?: defaultValue
}

fun <T> RedisTemplate<String, T>.existsKey(key: String): Boolean {
    return hasKey(key)
}

fun <T> RedisTemplate<String, T>.deleteIfExists(key: String): Boolean {
    return if (hasKey(key)) {
        delete(key)
    } else {
        false
    }
}

// ValueOperations 확장함수들
fun <T> ValueOperations<String, T>.setIfAbsent(key: String, value: T, duration: Duration): Boolean {
    return setIfAbsent(key, value, duration) ?: false
}

// 키 패턴 유틸리티
fun generateKey(prefix: String, vararg parts: Any): String {
    return "$prefix:${parts.joinToString(":")}"
}
