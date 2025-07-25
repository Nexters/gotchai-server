package com.gotchai.api.global.jwt

import com.gotchai.api.global.config.AuthenticationProperties
import com.gotchai.common.enum.auth.AuthorityType
import com.gotchai.common.enum.auth.TokenStatus
import com.gotchai.domain.auth.AuthenticationHistory
import com.gotchai.domain.auth.AuthenticationHistoryReader
import com.gotchai.domain.auth.AuthenticationHistoryUpdater
import com.gotchai.domain.auth.GrantedAuthority
import com.gotchai.domain.auth.Provider
import com.gotchai.domain.auth.ProviderDetail
import com.gotchai.domain.auth.RedisTokenRepository
import com.gotchai.domain.auth.Token
import com.gotchai.domain.auth.TokenRepository
import com.gotchai.domain.global.exception.AuthenticationErrorException
import com.gotchai.domain.global.exception.AuthenticationErrorType
import com.gotchai.domain.user.SocialUser
import com.gotchai.domain.user.User
import org.springframework.stereotype.Service

@Service
class TokenProcessor(
    private val jwtProvider: JwtProvider,
    private val authenticationProperties: AuthenticationProperties,
    private val redisTokenRepository: RedisTokenRepository,
    private val authenticationHistoryReader: AuthenticationHistoryReader,
    private val authenticationHistoryUpdater: AuthenticationHistoryUpdater,
) : TokenRepository {
    override fun create(
        deviceId: String?,
        user: User.Issue,
    ): Token {
        val accessToken =
            jwtProvider.issueAccessToken(
                user.id.toString(),
                JwtProvider.grantedAuthorities,
            )
        val refreshToken = jwtProvider.issueRefreshToken(user.id.toString())

        return Token(
            accessToken = accessToken,
            refreshToken = refreshToken,
        ).apply {
            redisTokenRepository.create(
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
    ): Token {
        val accessToken =
            jwtProvider.issueAccessToken(
                socialUser.id.toString(),
                JwtProvider.grantedAuthorities,
            )
        val refreshToken = jwtProvider.issueRefreshToken(socialUser.id.toString())

        return Token(
            accessToken = accessToken,
            refreshToken = refreshToken,
        ).apply {
            redisTokenRepository.create(
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

    override fun refresh(refreshToken: String): Token {
        val jwt = jwtProvider.validateToken(refreshToken)
        val tokenWithAuthentication = redisTokenRepository.findByToken(jwt.tokenValue)
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

        return Token(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
        ).apply {
            redisTokenRepository.create(
                accessToken = this.accessToken,
                refreshToken = this.refreshToken,
                deviceId = tokenWithAuthentication.deviceId,
                providerDetail = tokenWithAuthentication.provider,
                accessTokenExpiration = authenticationProperties.accessTokenExpirationSeconds,
                refreshTokenExpiration = authenticationProperties.refreshTokenExpirationSeconds,
            )

            authenticationHistoryUpdater.update(
                AuthenticationHistory.Update(
                    userId = authenticationHistory.authenticationId,
                    deviceId = authenticationHistory.deviceId,
                    refreshToken = refreshToken,
                    token =
                        Token(
                            accessToken = this.accessToken,
                            refreshToken = this.refreshToken,
                        ),
                ),
            )
        }
    }

    override fun remove(token: String): String {
        val jwt = jwtProvider.validateToken(token)
        authenticationHistoryUpdater.remove(token).apply {
            redisTokenRepository.deleteAllToken(this)
        }
        return jwt.id
    }

    override fun removeByUserId(userId: Long) {
        authenticationHistoryUpdater.removeToken(userId).map {
            redisTokenRepository.deleteAllToken(it)
        }
    }

    override fun findBy(accessToken: String): Provider? = redisTokenRepository.findBy(accessToken)

    private fun verifyTokenHistory(
        userId: Long,
        deviceId: String?,
        refreshToken: String,
    ): AuthenticationHistory.Info {
        val authenticationHistory =
            authenticationHistoryReader.readByUserWithDeviceWithRefreshToken(
                userId = userId,
                deviceId = deviceId,
                refreshToken = refreshToken,
            )

        if (authenticationHistory.status == TokenStatus.INACTIVE) {
            throw AuthenticationErrorException(AuthenticationErrorType.INVALID_TOKEN)
        }
        return authenticationHistory
    }

    private fun removeRotationToken(
        accessToken: String,
        refreshToken: String,
    ) {
        redisTokenRepository.deleteToken(accessToken)
        redisTokenRepository.deleteToken(refreshToken)
    }
}
