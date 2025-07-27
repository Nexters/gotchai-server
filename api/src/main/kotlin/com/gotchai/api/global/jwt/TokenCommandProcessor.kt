package com.gotchai.api.global.jwt

import com.gotchai.api.global.config.AuthenticationProperties
import com.gotchai.domain.auth.dto.AuthorityType
import com.gotchai.domain.auth.dto.GrantedAuthority
import com.gotchai.domain.auth.dto.ProviderDetail
import com.gotchai.domain.auth.dto.TokenPair
import com.gotchai.domain.auth.entity.AuthenticationHistory
import com.gotchai.domain.auth.entity.TokenStatus
import com.gotchai.domain.auth.exception.AuthenticationHistoryNotFoundException
import com.gotchai.domain.auth.port.out.AuthenticationHistoryCommandPort
import com.gotchai.domain.auth.port.out.AuthenticationHistoryQueryPort
import com.gotchai.domain.auth.port.out.RedisTokenCommandPort
import com.gotchai.domain.auth.port.out.RedisTokenQueryPort
import com.gotchai.domain.auth.port.out.TokenCommandPort
import com.gotchai.domain.global.exception.AuthenticationErrorException
import com.gotchai.domain.global.exception.AuthenticationErrorType
import com.gotchai.domain.user.dto.SocialUser
import com.gotchai.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class TokenCommandProcessor(
    private val jwtProvider: JwtProvider,
    private val authenticationProperties: AuthenticationProperties,
    private val redisTokenQueryPort: RedisTokenQueryPort,
    private val redisTokenCommandPort: RedisTokenCommandPort,
    private val authenticationHistoryQueryPort: AuthenticationHistoryQueryPort,
    private val authenticationHistoryCommandPort: AuthenticationHistoryCommandPort,
) : TokenCommandPort {
    override fun create(
        deviceId: String?,
        user: User.Issue,
    ): TokenPair {
        val accessToken =
            jwtProvider.issueAccessToken(
                user.id.toString(),
                JwtProvider.grantedAuthorities,
            )
        val refreshToken = jwtProvider.issueRefreshToken(user.id.toString())

        return TokenPair(
            accessToken = accessToken,
            refreshToken = refreshToken,
        ).apply {
            redisTokenCommandPort.create(
                accessToken = this.accessToken,
                refreshToken = this.refreshToken,
                deviceId = deviceId,
                providerDetail =
                    ProviderDetail(
                        user.id,
                        grantedAuthorities =
                            JwtProvider.grantedAuthorities.map {
                                it.authorityType.name
                            },
                    ),
                accessTokenExpiration = authenticationProperties.accessTokenExpirationSeconds,
                refreshTokenExpiration = authenticationProperties.refreshTokenExpirationSeconds,
            )
        }
    }

    override fun create(
        deviceId: String?,
        socialUser: SocialUser,
    ): TokenPair {
        val accessToken =
            jwtProvider.issueAccessToken(
                socialUser.id.toString(),
                JwtProvider.grantedAuthorities,
            )
        val refreshToken = jwtProvider.issueRefreshToken(socialUser.id.toString())

        return TokenPair(
            accessToken = accessToken,
            refreshToken = refreshToken,
        ).apply {
            redisTokenCommandPort.create(
                accessToken = this.accessToken,
                refreshToken = this.refreshToken,
                deviceId = deviceId,
                providerDetail =
                    ProviderDetail(
                        socialUser.id,
                        grantedAuthorities =
                            JwtProvider.grantedAuthorities.map {
                                it.authorityType.name
                            },
                    ),
                accessTokenExpiration = authenticationProperties.accessTokenExpirationSeconds,
                refreshTokenExpiration = authenticationProperties.refreshTokenExpirationSeconds,
            )
        }
    }

    override fun refresh(refreshToken: String): TokenPair {
        val jwt = jwtProvider.validateToken(refreshToken)
        val tokenWithAuthentication = redisTokenQueryPort.findByToken(jwt.tokenValue)
        val authenticationHistory =
            verifyTokenHistory(
                userId = tokenWithAuthentication.provider.userId,
                deviceId = tokenWithAuthentication.deviceId,
                refreshToken = tokenWithAuthentication.refreshToken,
            )

        removeRotationToken(tokenWithAuthentication.accessToken, tokenWithAuthentication.refreshToken)

        val newAccessToken =
            jwtProvider.issueAccessToken(
                jwtId = tokenWithAuthentication.provider.userId.toString(),
                grantedAuthorities =
                    tokenWithAuthentication.provider.grantedAuthorities.map {
                        GrantedAuthority(AuthorityType.valueOf(it))
                    },
            )
        val newRefreshToken =
            jwtProvider.issueRefreshToken(
                jwtId = tokenWithAuthentication.provider.userId.toString(),
            )

        return TokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
        ).apply {
            redisTokenCommandPort.create(
                accessToken = this.accessToken,
                refreshToken = this.refreshToken,
                deviceId = tokenWithAuthentication.deviceId,
                providerDetail = tokenWithAuthentication.provider,
                accessTokenExpiration = authenticationProperties.accessTokenExpirationSeconds,
                refreshTokenExpiration = authenticationProperties.refreshTokenExpirationSeconds,
            )

            authenticationHistoryCommandPort.update(
                AuthenticationHistory.Update(
                    userId = authenticationHistory.authenticationId,
                    deviceId = authenticationHistory.deviceId,
                    refreshToken = refreshToken,
                    tokenPair =
                        TokenPair(
                            accessToken = this.accessToken,
                            refreshToken = this.refreshToken,
                        ),
                ),
            )
        }
    }

    override fun remove(token: String): String {
        val jwt = jwtProvider.validateToken(token)
        authenticationHistoryCommandPort.remove(token).apply {
            redisTokenCommandPort.deleteAllToken(this)
        }
        return jwt.id
    }

    override fun removeByUserId(userId: Long) {
        authenticationHistoryCommandPort.removeToken(userId).map {
            redisTokenCommandPort.deleteAllToken(it)
        }
    }

    private fun verifyTokenHistory(
        userId: Long,
        deviceId: String?,
        refreshToken: String,
    ): AuthenticationHistory.Info {
        val authenticationHistory =
            authenticationHistoryQueryPort.findUserIdWithDeviceWithRefreshToken(
                userId = userId,
                deviceId = deviceId,
                refreshToken = refreshToken,
            ) ?: throw AuthenticationHistoryNotFoundException()

        if (authenticationHistory.status == TokenStatus.INACTIVE) {
            throw AuthenticationErrorException(AuthenticationErrorType.INVALID_TOKEN)
        }
        return authenticationHistory
    }

    private fun removeRotationToken(
        accessToken: String,
        refreshToken: String,
    ) {
        redisTokenCommandPort.deleteToken(accessToken)
        redisTokenCommandPort.deleteToken(refreshToken)
    }
}
