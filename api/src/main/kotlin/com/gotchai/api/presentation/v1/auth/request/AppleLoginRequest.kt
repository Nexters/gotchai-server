package com.gotchai.api.presentation.v1.auth.request

import com.gotchai.domain.auth.dto.command.SocialLoginCommand
import com.gotchai.domain.user.entity.SocialProvider

data class AppleLoginRequest(
    val idToken: String
) {
    fun toCommand(): SocialLoginCommand =
        SocialLoginCommand(
            oAuthToken = idToken,
            provider = SocialProvider.APPLE
        )
}
