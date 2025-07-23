package com.gotchai.api.global.jwt
import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class CustomJwtGrantedAuthoritiesConverter : Converter<Jwt, Collection<GrantedAuthority>> {
    override fun convert(jwt: Jwt): Collection<GrantedAuthority> {
        val roles: List<String> = jwt.getClaim("ROLE") ?: emptyList()
        return roles.map { role -> SimpleGrantedAuthority(role) }
    }
}
