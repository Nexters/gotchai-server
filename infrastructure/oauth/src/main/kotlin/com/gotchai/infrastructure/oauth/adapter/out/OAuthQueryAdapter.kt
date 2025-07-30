package com.gotchai.infrastructure.oauth.adapter.out

import com.gotchai.domain.auth.dto.OAuthUser
import com.gotchai.domain.auth.port.out.OAuthQueryPort
import com.gotchai.domain.user.entity.SocialProvider
import com.gotchai.infrastructure.oauth.annotation.Adapter
import com.gotchai.infrastructure.oauth.client.AppleClient
import com.gotchai.infrastructure.oauth.client.KakaoClient
import com.gotchai.infrastructure.oauth.exception.IdTokenVerificationException

@Adapter
class OAuthQueryAdapter(
    private val kakaoClient: KakaoClient,
    private val appleClient: AppleClient
) : OAuthQueryPort {
    override fun getOAuthUserByToken(
        token: String,
        provider: SocialProvider
    ): OAuthUser =
        when (provider) {
            SocialProvider.KAKAO -> getKakaoUserByToken(token)
            SocialProvider.APPLE -> getAppleUserByToken(token)
        }

    private fun getKakaoUserByToken(accessToken: String): OAuthUser =
        kakaoClient
            .getKakaoUserByToken(accessToken)
            .toOAuthUser()

    private fun getAppleUserByToken(idToken: String): OAuthUser {
        if (!appleClient.verify(idToken)) throw IdTokenVerificationException()

        return appleClient
            .getAppleUserByToken(idToken)
            .toOAuthUser()
    }
}
