package com.gotchai.infrastructure.oauth.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.gotchai.domain.auth.exception.InvalidOAuthTokenException
import com.gotchai.infrastructure.oauth.config.AppleProperties
import com.gotchai.infrastructure.oauth.dto.ApplePublicKeyResponse
import com.gotchai.infrastructure.oauth.dto.AppleUser
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.SignedJWT
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.requiredBody
import java.text.ParseException
import java.util.*

@Component
class AppleClient(
    private val restClient: RestClient,
    private val objectMapper: ObjectMapper,
    private val appleProperties: AppleProperties
) {
    companion object {
        private const val APPLE_URI = "https://appleid.apple.com"
    }

    fun getAppleUserByToken(token: String): AppleUser =
        try {
            val signedJWT = SignedJWT.parse(token)
            val jwtClaims = signedJWT.jwtClaimsSet

            AppleUser(
                id = jwtClaims.getStringClaim("sub"),
                email = jwtClaims.getStringClaim("email")
            )
        } catch (ex: ParseException) {
            throw InvalidOAuthTokenException()
        }

    fun verify(token: String): Boolean =
        runCatching {
            val signedJwt = SignedJWT.parse(token)
            val claimSet = signedJwt.jwtClaimsSet
            val now = Date()

            if (!isSignatureValid(signedJwt)) return false
            if (claimSet.audience.firstOrNull() != appleProperties.bundleId) return false

            now.before(claimSet.expirationTime) && claimSet.issuer == APPLE_URI
        }.getOrElse { false }

    private fun isSignatureValid(signedJWT: SignedJWT): Boolean =
        getPublicKeys()
            .keys
            .map {
                val rsaKey = JWK.parse(objectMapper.writeValueAsString(it)) as RSAKey
                val publicKey = rsaKey.toRSAPublicKey()

                RSASSAVerifier(publicKey)
            }.any { signedJWT.verify(it) }

    private fun getPublicKeys(): ApplePublicKeyResponse =
        restClient
            .get()
            .uri("$APPLE_URI/auth/keys")
            .retrieve()
            .requiredBody<ApplePublicKeyResponse>()
}
