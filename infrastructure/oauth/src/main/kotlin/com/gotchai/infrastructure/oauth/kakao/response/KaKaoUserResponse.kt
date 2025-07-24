package com.gotchai.infrastructure.oauth.kakao.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.gotchai.infrastructure.oauth.kakao.KaKaoClientResult

@JsonIgnoreProperties(ignoreUnknown = true)
data class KaKaoUserResponse(
    val id: String,
    @field:JsonProperty("kakao_account")
    val kaKaoAccount: KaKaoAccount,
) {
    fun toResult(): KaKaoClientResult =
        KaKaoClientResult(
            id = id,
            email = kaKaoAccount.email ?: "",
            name = kaKaoAccount.name ?: "gotchai",
            nickname = kaKaoAccount.kaKaoProfile.nickname ?: "gotchai",
        )
}
