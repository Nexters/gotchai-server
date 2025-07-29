package com.gotchai.api.presentation.v1.auth.request

import com.gotchai.domain.auth.dto.command.SocialLoginCommand
import com.gotchai.domain.user.entity.SocialProvider

data class KakaoLoginRequest(
    val accessToken: String
) {
    fun toCommand(): SocialLoginCommand =
        SocialLoginCommand(
            oAuthToken = accessToken,
            provider = SocialProvider.KAKAO
        )
}
