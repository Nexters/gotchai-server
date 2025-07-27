package com.gotchai.api.global.jwt

import com.gotchai.domain.auth.dto.Provider
import com.gotchai.domain.auth.port.out.RedisTokenQueryPort
import com.gotchai.domain.auth.port.out.TokenQueryPort
import org.springframework.stereotype.Service

@Service
class TokenQueryProcessor(
    private val redisTokenQueryPort: RedisTokenQueryPort,
) : TokenQueryPort {
    override fun findBy(accessToken: String): Provider? = redisTokenQueryPort.findBy(accessToken)
}
