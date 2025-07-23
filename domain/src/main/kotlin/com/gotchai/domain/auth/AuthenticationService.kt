package com.gotchai.domain.auth

import com.gotchai.domain.auth.token.RefreshToken
import com.gotchai.domain.auth.token.Token
import com.gotchai.domain.user.SocialUser
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val authenticationProcessor: AuthenticationProcessor,
) {
    fun socialLogin(
        deviceId: String,
        socialUser: SocialUser,
    ): Token =
        authenticationProcessor.login(
            deviceId = deviceId,
            socialUser = socialUser,
        )

    fun renew(refreshToken: RefreshToken): Token = authenticationProcessor.renew(refreshToken.token)

    fun logout(token: String): String = authenticationProcessor.remove(token)

    fun withdrawUser(userId: Long) {
        authenticationProcessor.withdrawal(userId)
    }
}
