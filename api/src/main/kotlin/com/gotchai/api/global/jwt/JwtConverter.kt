package com.gotchai.api.global.jwt

import com.gotchai.domain.auth.dto.Provider
import com.gotchai.domain.auth.port.out.RedisTokenCommandPort
import com.gotchai.domain.auth.port.out.RedisTokenQueryPort
import com.gotchai.domain.global.exception.AuthenticationErrorException
import com.gotchai.domain.global.exception.AuthenticationErrorType
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.util.Assert

class JwtConverter(
    private val redisTokenQueryPort: RedisTokenQueryPort,
    private val redisTokenCommandPort: RedisTokenCommandPort,
) : Converter<Jwt, AbstractAuthenticationToken> {
    private var customJwtGrantedAuthoritiesConverter: Converter<Jwt, Collection<GrantedAuthority>> =
        CustomJwtGrantedAuthoritiesConverter()

    private var principalClaimName: String = JwtClaimNames.SUB

    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        val authority: Collection<GrantedAuthority>? = customJwtGrantedAuthoritiesConverter.convert(jwt)
        val provider: Provider = findProvider(jwt)
        return UsernamePasswordAuthenticationToken(provider, null, authority)
    }

    private fun setCustomJwtGrantedAuthoritiesConverter(
        customJwtGrantedAuthoritiesConverter: Converter<Jwt, Collection<GrantedAuthority>>,
    ) {
        Assert.notNull(customJwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null")
        this.customJwtGrantedAuthoritiesConverter = customJwtGrantedAuthoritiesConverter
    }

    fun setPrincipalClaimName(principalClaimName: String) {
        Assert.hasText(principalClaimName, "principalClaimName cannot be empty")
        this.principalClaimName = principalClaimName
    }

    private fun findProvider(jwt: Jwt): Provider {
        val tokenWithAuthentication = redisTokenQueryPort.findByToken(jwt.tokenValue)
        jwt.validateByCachedToken(tokenWithAuthentication.accessToken)
        return Provider(
            userId = tokenWithAuthentication.provider.userId,
        )
    }

    private fun Jwt.validateByCachedToken(token: String) {
        if (this.tokenValue == token) {
            return
        }

        redisTokenCommandPort.deleteAllToken(this.tokenValue)
        throw AuthenticationErrorException(AuthenticationErrorType.INVALID_TOKEN)
    }
}
