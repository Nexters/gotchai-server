package com.gotchai.api.global.jwt

import com.gotchai.api.global.config.JwtProperties
import com.gotchai.domain.global.provider.TokenProvider
import com.gotchai.domain.global.security.GotchaiAuthentication
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.SIG
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties
) : TokenProvider {
    override fun createAccessToken(authentication: GotchaiAuthentication) =
        createToken(jwtProperties.accessTokenExpiration, authentication.toPayload())

    override fun createRefreshToken(authentication: GotchaiAuthentication) =
        createToken(jwtProperties.refreshTokenExpiration, authentication.toPayload())

    override fun getAuthentication(token: String): GotchaiAuthentication =
        getPayload(token)
            .let { GotchaiAuthentication.from(it) }

    private fun createToken(
        expiration: Duration,
        claims: Map<String, *>
    ): String {
        val now = Date()

        return Jwts
            .builder()
            .issuedAt(now)
            .expiration(Date(now.time + expiration.toMillis()))
            .issuer("Gotchai")
            .claims(claims)
            .signWith(jwtProperties.privateKey, SIG.RS512)
            .compact()
    }

    private fun getPayload(token: String): Map<String, *> =
        Jwts
            .parser()
            .verifyWith(jwtProperties.publicKey)
            .build()
            .parseSignedClaims(token)
            .payload
}
