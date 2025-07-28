package com.gotchai.infrastructure.oauth.adapter.out

import com.gotchai.domain.auth.dto.OAuthUser
import com.gotchai.domain.auth.exception.InvalidOAuthTokenException
import com.gotchai.domain.auth.port.out.OAuthQueryPort
import com.gotchai.domain.user.entity.SocialProvider
import com.gotchai.infrastructure.oauth.client.AppleClient
import com.gotchai.infrastructure.oauth.client.KakaoClient
import org.springframework.stereotype.Service

@Service
class OAuthQueryAdapter(
    private val kakaoClient: KakaoClient,
    private val appleClient: AppleClient,
) : OAuthQueryPort {
    override fun getOAuthUserByToken(token: String, provider: SocialProvider): OAuthUser =
        when (provider) {
            SocialProvider.KAKAO -> getKakaoEmailByToken(token)
            SocialProvider.APPLE -> getAppleEmailByToken(token)
        }

    private fun getKakaoEmailByToken(accessToken: String): OAuthUser =
        kakaoClient.getKakaoUserByToken(accessToken)
            .toOAuthUser()

    private fun getAppleEmailByToken(idToken: String): OAuthUser {
        if (!appleClient.verify(idToken)) throw InvalidOAuthTokenException()

        return appleClient.getAppleUserByToken(idToken)
            .toOAuthUser()
    }
}
