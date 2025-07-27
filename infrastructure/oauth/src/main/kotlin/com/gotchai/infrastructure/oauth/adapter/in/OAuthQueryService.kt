package com.gotchai.infrastructure.oauth.adapter.`in`

import com.gotchai.domain.auth.exception.InvalidAppleTokenException
import com.gotchai.domain.auth.exception.InvalidKakaoTokenException
import com.gotchai.infrastructure.oauth.apple.AppleClient
import com.gotchai.infrastructure.oauth.apple.AppleClientResult
import com.gotchai.infrastructure.oauth.kakao.KaKaoClient
import com.gotchai.infrastructure.oauth.kakao.KaKaoClientResult
import com.gotchai.infrastructure.oauth.port.`in`.OAuthQueryUseCase
import feign.FeignException
import org.springframework.stereotype.Service

@Service
class OAuthQueryService(
    private val kaKaoClient: KaKaoClient,
    private val appleClient: AppleClient,
) : OAuthQueryUseCase {
    override fun getKaKaoUserInfo(accessToken: String): KaKaoClientResult =
        try {
            kaKaoClient.getUserInfo(accessToken)
        } catch (e: FeignException) {
            if (e.status() == 401) {
                throw InvalidKakaoTokenException()
            } else {
                throw InvalidKakaoTokenException(e.message ?: "카카오 API 호출 중 오류가 발생했습니다.")
            }
        }

    override fun getAppleUserInfo(idToken: String): AppleClientResult {
        if (!appleClient.verify(idToken)) throw InvalidAppleTokenException()
        return appleClient.getUserInfo(idToken)
    }
}
