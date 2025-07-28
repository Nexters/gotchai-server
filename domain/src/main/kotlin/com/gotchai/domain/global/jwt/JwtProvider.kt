package com.gotchai.domain.global.jwt

import com.gotchai.domain.global.security.GotchaiAuthentication

interface JwtProvider {
    fun createAccessToken(authentication: GotchaiAuthentication): String

    fun createRefreshToken(authentication: GotchaiAuthentication): String

    fun getAuthentication(token: String): GotchaiAuthentication
}
