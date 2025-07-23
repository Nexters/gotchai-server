package com.gotchai.domain.auth

import com.gotchai.domain.auth.token.NewToken
import com.gotchai.domain.auth.token.Token
import com.gotchai.domain.auth.token.TokenRepository
import com.gotchai.domain.auth.token.TokenStatus
import com.gotchai.domain.user.SocialUser
import org.springframework.stereotype.Component

@Component
class AuthenticationProcessor(
    private val tokenRepository: TokenRepository,
    private val authenticationHistoryWriter: AuthenticationHistoryWriter,
) {
    fun login(
        deviceId: String,
        socialUser: SocialUser,
    ): Token =
        tokenRepository.create(deviceId, socialUser).apply {
            authenticationHistoryWriter.write(
                AuthenticationHistory.Creation(
                    userId = socialUser.id,
                    deviceId = deviceId,
                    newToken =
                        NewToken(
                            Token(
                                accessToken = this.accessToken,
                                refreshToken = this.refreshToken,
                            ),
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
