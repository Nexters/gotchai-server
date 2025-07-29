package com.gotchai.infrastructure.oauth.client

import com.gotchai.domain.auth.exception.InvalidOAuthTokenException
import com.gotchai.infrastructure.oauth.dto.KakaoUser
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

@Component
class KakaoClient(
    private val webClient: WebClient,
) {
    companion object {
        private const val KAKAO_URI = "https://kapi.kakao.com"
    }

    fun getKakaoUserByToken(accessToken: String): KakaoUser =
        webClient.get()
            .uri("$KAKAO_URI/v2/user/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer $accessToken")
            .retrieve()
            .bodyToMono<KakaoUser>()
            .onErrorMap { InvalidOAuthTokenException() }
            .block()!!
}
