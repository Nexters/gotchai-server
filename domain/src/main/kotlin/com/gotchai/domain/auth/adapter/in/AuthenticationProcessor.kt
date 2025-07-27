package com.gotchai.domain.auth.adapter.`in`

import com.gotchai.domain.auth.dto.Token
import com.gotchai.domain.auth.entity.AuthenticationHistory
import com.gotchai.domain.auth.entity.TokenStatus
import com.gotchai.domain.auth.port.out.AuthenticationHistoryCommandPort
import com.gotchai.domain.auth.port.out.TokenCommandPort
import com.gotchai.domain.user.dto.SocialUser
import com.gotchai.domain.user.entity.User
import org.springframework.stereotype.Component

@Component
class AuthenticationProcessor(
    private val tokenCommandPort: TokenCommandPort,
    private val authenticationHistoryCommandPort: AuthenticationHistoryCommandPort,
) {
    fun login(user: User.Issue): Token =
        tokenCommandPort.create(null, user).apply {
            authenticationHistoryCommandPort.create(
                AuthenticationHistory.Creation(
                    userId = user.id,
                    deviceId = null,
                    token =
                        Token(
                            accessToken = this.accessToken,
                            refreshToken = this.refreshToken,
                        ),
                    status = TokenStatus.ACTIVE,
                ),
            )
        }

    fun login(
        deviceId: String?,
        socialUser: SocialUser,
    ): Token =
        tokenCommandPort.create(deviceId, socialUser).apply {
            authenticationHistoryCommandPort.create(
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

    fun renew(refreshToken: String): Token = tokenCommandPort.refresh(refreshToken)

    fun remove(token: String): String = tokenCommandPort.remove(token)

    fun withdrawal(userId: Long) {
        tokenCommandPort.removeByUserId(userId)
    }
}
