package com.gotchai.domain.auth.dto

import com.gotchai.domain.user.entity.SocialProvider

data class OAuthUser(
    val id: String,
    val email: String,
    val provider: SocialProvider
)
