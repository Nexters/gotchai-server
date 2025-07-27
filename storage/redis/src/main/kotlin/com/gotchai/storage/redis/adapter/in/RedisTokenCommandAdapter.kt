package com.gotchai.storage.redis.adapter.`in`

import com.fasterxml.jackson.databind.ObjectMapper
import com.gotchai.domain.auth.dto.ProviderDetail
import com.gotchai.domain.auth.dto.TokenWithAuthentication
import com.gotchai.domain.auth.exception.InvalidTokenException
import com.gotchai.domain.auth.port.out.RedisTokenCommandPort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisTokenCommandAdapter(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : RedisTokenCommandPort {
    override fun create(
        accessToken: String,
        refreshToken: String,
        deviceId: String?,
        providerDetail: ProviderDetail,
        accessTokenExpiration: Long,
        refreshTokenExpiration: Long,
    ): TokenWithAuthentication {
        val tokenWithAuthentication =
            TokenWithAuthentication(
                accessToken = accessToken,
                refreshToken = refreshToken,
                deviceId = deviceId,
                provider = providerDetail,
            )

        redisTemplate.opsForValue().apply {
            set(
                accessToken,
                objectMapper.writeValueAsString(tokenWithAuthentication),
                Duration.ofSeconds(accessTokenExpiration * 60L),
            )
            set(
                refreshToken,
                objectMapper.writeValueAsString(tokenWithAuthentication),
                Duration.ofSeconds(refreshTokenExpiration * 60L),
            )
        }
        return tokenWithAuthentication
    }

    override fun deleteToken(token: String) {
        redisTemplate.delete(token)
    }

    override fun deleteAllToken(token: String) {
        val tokenWithAuthentication =
            redisTemplate.opsForValue().get(token)?.let {
                objectMapper.readValue(it, TokenWithAuthentication::class.java)
            } ?: throw InvalidTokenException()

        redisTemplate.delete(tokenWithAuthentication.accessToken)
        redisTemplate.delete(tokenWithAuthentication.refreshToken)
    }
}
