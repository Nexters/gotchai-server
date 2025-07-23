package com.gotchai.api.global.jwt

import com.gotchai.api.global.config.AuthenticationProperties
import com.gotchai.domain.auth.AuthenticationHistory
import com.gotchai.domain.auth.AuthenticationHistoryReader
import com.gotchai.domain.auth.AuthenticationHistoryUpdater
import com.gotchai.domain.auth.AuthorityType
import com.gotchai.domain.auth.GrantedAuthority
import com.gotchai.domain.auth.Provider
import com.gotchai.domain.auth.ProviderDetail
import com.gotchai.domain.auth.RedisTokenRepository
import com.gotchai.domain.auth.token.NewToken
import com.gotchai.domain.auth.token.Token
import com.gotchai.domain.auth.token.TokenRepository
import com.gotchai.domain.auth.token.TokenStatus
import com.gotchai.domain.global.exception.AuthenticationErrorException
import com.gotchai.domain.global.exception.AuthenticationErrorType
import com.gotchai.domain.user.SocialUser
import com.gotchai.domain.user.User
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.oauth2.jwt.BadJwtException
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.stereotype.Component
import java.time.Instant
import javax.naming.AuthenticationException

@Component
class JwtProvider(
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder,
    private val authenticationProperties: AuthenticationProperties,
    private val redisTokenRepository: RedisTokenRepository,
    private val authenticationHistoryReader: AuthenticationHistoryReader,
    private val authenticationHistoryUpdater: AuthenticationHistoryUpdater,
) : TokenRepository {
    companion object {
        val grantedAuthorities = listOf(GrantedAuthority(AuthorityType.USER))
    }

    override fun create(
        deviceId: String?,
        user: User.Issue,
    ): Token {
        val accessToken =
            issueAccessToken(
                user.id.toString(),
                grantedAuthorities,
            )
        val refreshToken = issueRefreshToken(user.id.toString())
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
                            grantedAuthorities.map {
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
            issueAccessToken(
                socialUser.id.toString(),
                grantedAuthorities,
            )
        val refreshToken = issueRefreshToken(socialUser.id.toString())
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
                            grantedAuthorities.map {
                                it.authorityType.name
                            },
                    ),
                accessTokenExpiration = authenticationProperties.accessTokenExpirationSeconds,
                refreshTokenExpiration = authenticationProperties.refreshTokenExpirationSeconds,
            )
        }
    }

    override fun refresh(refreshToken: String): Token {
        val jwt = validateToken(refreshToken)
        val tokenWithAuthentication = redisTokenRepository.findByToken(jwt.tokenValue)
        val authenticationHistory =
            verifyTokenHistory(
                userId = tokenWithAuthentication.provider.userId,
                deviceId = tokenWithAuthentication.deviceId,
                refreshToken = tokenWithAuthentication.refreshToken,
            )

        removeRotationToken(tokenWithAuthentication.accessToken, tokenWithAuthentication.refreshToken)

        val newAccessToken =
            issueAccessToken(
                jwtId = tokenWithAuthentication.provider.userId.toString(),
                grantedAuthorities =
                    tokenWithAuthentication.provider.grantedAuthorities.map {
                        GrantedAuthority(AuthorityType.valueOf(it))
                    },
            )
        val newRefreshToken =
            issueRefreshToken(
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
                    newToken =
                        NewToken(
                            token =
                                Token(
                                    accessToken = this.accessToken,
                                    refreshToken = this.refreshToken,
                                ),
                        ),
                ),
            )
        }
    }

    override fun remove(token: String): String {
        val jwt = validateToken(token)
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

    @Throws(AuthenticationException::class)
    fun validateToken(token: String): Jwt =
        try {
            jwtDecoder.decode(token)
        } catch (exception: BadJwtException) {
            throw AuthenticationErrorException(AuthenticationErrorType.INVALID_TOKEN)
        } catch (exception: JwtException) {
            throw AuthenticationServiceException(exception.message, exception)
        }

    /**
     * issue access token
     *
     * @param jwtId [String] jwt identifier.
     */
    private fun issueAccessToken(
        jwtId: String,
        grantedAuthorities: List<GrantedAuthority>,
    ): String {
        val issuedAt: Instant = Instant.now()
        return generateToken(
            jwtId = jwtId,
            expiresAt = issuedAt.plusSeconds(authenticationProperties.accessTokenExpirationSeconds * 60L),
            issuedAt = issuedAt,
            claims = mapOf(Pair("type", "A"), Pair("roles", grantedAuthorities.map { it.authorityType.name })),
        )
    }

    /**
     * issue refresh token
     *
     * @param jwtId [String] jwt identifier.
     */
    private fun issueRefreshToken(jwtId: String): String {
        val issuedAt: Instant = Instant.now()
        return generateToken(
            jwtId = jwtId,
            expiresAt = issuedAt.plusSeconds(authenticationProperties.refreshTokenExpirationSeconds * 60L),
            issuedAt = issuedAt,
            claims = mapOf(Pair("type", "R")),
        )
    }

    private fun generateToken(
        jwtId: String,
        expiresAt: Instant,
        issuedAt: Instant,
        claims: Map<String, Any>? = emptyMap(),
    ): String {
        val jwtClaimsSet: JwtClaimsSet =
            JwtClaimsSet
                .builder()
                .id(jwtId)
                .expiresAt(expiresAt)
                .issuedAt(issuedAt)
                .issuer("gotchai")
                .claims {
                    if (claims != null) {
                        it.putAll(claims)
                    }
                }.build()
        try {
            return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).tokenValue
        } catch (e: IllegalArgumentException) {
            throw InvalidBearerTokenException(e.message)
        }
    }

    private fun verifyTokenHistory(
        userId: Long,
        deviceId: String?,
        refreshToken: String,
    ): AuthenticationHistory.Info {
        val authenticationHistory =
            authenticationHistoryReader.readByUserKeyWithDeviceWithRefreshToken(
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
