package com.gotchai.infrastructure.oauth.dto

import com.gotchai.domain.auth.dto.OAuthUser
import com.gotchai.domain.user.entity.SocialProvider

data class AppleUser(
    val id: String,
    val email: String,
) {
    fun toOAuthUser(): OAuthUser =
        OAuthUser(
            id = id,
            email = email,
            provider = SocialProvider.APPLE
        )
}
