package com.gotchai.domain.auth.port.`in`

import com.gotchai.domain.auth.dto.Token
import com.gotchai.domain.user.dto.SocialUser

interface AuthenticationCommandUseCase {
    fun testLogin(
        email: String,
        password: String,
    ): Token

    fun testSignUp(
        name: String,
        email: String,
        password: String,
    )

    fun socialLogin(
        deviceId: String?,
        socialUser: SocialUser,
    ): Token

    fun renew(refreshToken: String): Token

    fun logout(token: String): String

    fun withdrawUser(userId: Long)
}
