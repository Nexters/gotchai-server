package com.gotchai.domain.global.provider

import com.gotchai.domain.global.security.GotchaiAuthentication

interface TokenProvider {
    fun createAccessToken(authentication: GotchaiAuthentication): String

    fun createRefreshToken(authentication: GotchaiAuthentication): String

    fun getAuthentication(token: String): GotchaiAuthentication
}
