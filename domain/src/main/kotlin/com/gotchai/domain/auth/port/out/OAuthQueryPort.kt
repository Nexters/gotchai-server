package com.gotchai.domain.auth.port.out

import com.gotchai.domain.auth.dto.OAuthUser
import com.gotchai.domain.user.entity.SocialProvider

interface OAuthQueryPort {
    fun getOAuthUserByToken(token: String, provider: SocialProvider): OAuthUser
}
