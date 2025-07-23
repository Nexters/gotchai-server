package com.gotchai.domain.auth

import com.gotchai.common.enum.auth.TokenStatus
import com.gotchai.domain.user.SocialUser
import org.springframework.stereotype.Component

@Component
class AuthenticationProcessor(
    private val tokenRepository: TokenRepository,
    private val authenticationHistoryAppender: AuthenticationHistoryAppender,
) {
    fun login(
        deviceId: String,
        socialUser: SocialUser,
    ): Token =
        tokenRepository.create(deviceId, socialUser).apply {
            authenticationHistoryAppender.write(
                AuthenticationHistory.Creation(
                    userId = socialUser.id,
                    deviceId = deviceId,
                    token =
                        Token(
                            accessToken = this.accessToken,
                            refreshToken = this.refreshToken,
                        ),
                    status = TokenStatus.ACTIVE,
                ),
            )
        }

    fun renew(refreshToken: String): Token = tokenRepository.refresh(refreshToken)

    fun remove(token: String): String = tokenRepository.remove(token)

    fun withdrawal(userId: Long) {
        tokenRepository.removeByUserId(userId)
    }
}
