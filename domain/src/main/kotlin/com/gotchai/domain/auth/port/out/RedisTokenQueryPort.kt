package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.dto.Provider
import com.gotchai.domain.auth.dto.TokenWithAuthentication

interface RedisTokenQueryPort {
    fun findByToken(token: String): TokenWithAuthentication

    fun findBy(accessToken: String): Provider?
}
