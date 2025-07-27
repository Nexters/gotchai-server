package com.gotchai.storage.redis.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.gotchai.domain.auth.dto.Provider
import com.gotchai.domain.auth.dto.TokenWithAuthentication
import com.gotchai.domain.auth.exception.InvalidTokenException
import com.gotchai.domain.auth.port.out.RedisTokenQueryPort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisTokenQueryAdapter(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : RedisTokenQueryPort {
    override fun findByToken(token: String): TokenWithAuthentication {
        redisTemplate.opsForValue().get(token)?.let {
            return objectMapper.readValue(it, TokenWithAuthentication::class.java)
        } ?: throw InvalidTokenException()
    }

    override fun findBy(accessToken: String): Provider? =
        redisTemplate.opsForValue().get(accessToken)?.let {
            val tokenWithAuthentication = objectMapper.readValue(it, TokenWithAuthentication::class.java)
            Provider(
                userId = tokenWithAuthentication.provider.userId,
            )
        }
}
