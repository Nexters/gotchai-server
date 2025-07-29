package com.gotchai.infrastructure.oauth.client

import com.gotchai.infrastructure.oauth.dto.KakaoUser
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.requiredBody

@Component
class KakaoClient(
    private val restClient: RestClient
) {
    companion object {
        private const val KAKAO_URI = "https://kapi.kakao.com"
    }

    fun getKakaoUserByToken(accessToken: String): KakaoUser =
        restClient
            .get()
            .uri("$KAKAO_URI/v2/user/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .requiredBody<KakaoUser>()
}
