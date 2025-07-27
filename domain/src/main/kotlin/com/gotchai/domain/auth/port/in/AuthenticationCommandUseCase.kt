package com.gotchai.domain.auth.port.`in`

import com.gotchai.domain.auth.dto.TokenPair
import com.gotchai.domain.user.entity.SocialType

interface AuthenticationCommandUseCase {
    fun testLogin(
        email: String,
        password: String,
    ): TokenPair

    fun testSignUp(
        name: String,
        email: String,
        password: String,
    )

    fun socialLogin(
        deviceId: String?,
        email: String,
        socialId: String,
        socialType: SocialType,
    ): TokenPair

    fun renew(refreshToken: String): TokenPair

    fun logout(token: String): String

    fun withdrawUser(userId: Long)
}
