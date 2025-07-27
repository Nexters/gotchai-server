package com.gotchai.infrastructure.oauth.port.`in`

import com.gotchai.infrastructure.oauth.apple.AppleClientResult
import com.gotchai.infrastructure.oauth.kakao.KaKaoClientResult

interface OAuthQueryUseCase {
    fun getKaKaoUserInfo(accessToken: String): KaKaoClientResult

    fun getAppleUserInfo(idToken: String): AppleClientResult
}
