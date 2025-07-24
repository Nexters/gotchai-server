package com.gotchai.infrastructure.oauth

import com.gotchai.domain.global.exception.AuthenticationErrorException
import com.gotchai.domain.global.exception.AuthenticationErrorType
import com.gotchai.infrastructure.oauth.apple.AppleClient
import com.gotchai.infrastructure.oauth.apple.AppleClientResult
import com.gotchai.infrastructure.oauth.kakao.KaKaoClient
import com.gotchai.infrastructure.oauth.kakao.KaKaoClientResult
import feign.FeignException
import org.springframework.stereotype.Service

@Service
class OAuthService(
    private val kaKaoClient: KaKaoClient,
    private val appleClient: AppleClient,
) {
    fun getKaKaoUserInfo(accessToken: String): KaKaoClientResult =
        try {
            kaKaoClient.getUserInfo(accessToken)
        } catch (e: FeignException) {
            if (e.status() == 401) {
                throw AuthenticationErrorException(AuthenticationErrorType.INVALID_KAKAO_TOKEN)
            } else {
                throw AuthenticationErrorException(AuthenticationErrorType.INVALID_KAKAO_TOKEN, e.message)
            }
        }

    fun getAppleUserInfo(idToken: String): AppleClientResult {
        if (!appleClient.verify(idToken)) throw AuthenticationErrorException(AuthenticationErrorType.INVALID_APPLE_TOKEN)
        return appleClient.getUserInfo(idToken)
    }
}
