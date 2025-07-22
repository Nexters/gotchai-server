package com.gotchai.api.global.config

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class JwtTokenProvider(
    private val rsaKeyProperties: RsaKeyProperties
) {

    fun generateToken(subject: String): String {
        val now = Instant.now()
        
        return Jwts.builder()
            .subject(subject)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(1, ChronoUnit.HOURS)))
            .signWith(rsaKeyProperties.privateKey)
            .compact()
    }
}