package com.gotchai.infrastructure.oauth.kakao.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.gotchai.infrastructure.oauth.kakao.KaKaoClientResult

@JsonIgnoreProperties(ignoreUnknown = true)
data class KaKaoUserInfoResponse(
    val id: Long,
    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount,
) {
    data class KakaoAccount(
        val email: String,
        val profile: Profile?,
    ) {
        data class Profile(
            val nickname: String?,
        )
    }

    fun toResult(): KaKaoClientResult =
        KaKaoClientResult(
            id = id.toString(),
            email = kakaoAccount.email,
            nickname = kakaoAccount.profile?.nickname,
        )
}
