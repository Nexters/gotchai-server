package com.gotchai.domain.auth.dto.command

import com.gotchai.domain.user.entity.SocialProvider

data class SocialLoginCommand(
    val oAuthToken: String,
    val provider: SocialProvider,
)
