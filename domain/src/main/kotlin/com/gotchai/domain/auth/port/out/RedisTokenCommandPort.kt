package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.dto.ProviderDetail
import com.gotchai.domain.auth.dto.TokenWithAuthentication

interface RedisTokenCommandPort {
    fun create(
        accessToken: String,
        refreshToken: String,
        deviceId: String?,
        providerDetail: ProviderDetail,
        accessTokenExpiration: Long,
        refreshTokenExpiration: Long,
    ): TokenWithAuthentication

    fun deleteToken(token: String)

    fun deleteAllToken(token: String)
}
