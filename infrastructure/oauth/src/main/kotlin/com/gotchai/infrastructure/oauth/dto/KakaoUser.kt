package com.gotchai.infrastructure.oauth.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.gotchai.domain.auth.dto.OAuthUser
import com.gotchai.domain.user.entity.SocialProvider

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoUser(
    val id: Long,
    @JsonProperty("kakao_account")
    val account: Account,
) {
    data class Account(
        val email: String,
        val profile: Profile?,
    ) {
        data class Profile(
            val nickname: String?,
        )
    }

    fun toOAuthUser(): OAuthUser =
        OAuthUser(
            id = id.toString(),
            email = account.email,
            provider = SocialProvider.KAKAO
        )
}
