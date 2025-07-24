package com.gotchai.infrastructure.oauth.kakao.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class KaKaoAccount(
    val email: String?,
    val name: String?,
    @JsonProperty("profile")
    val kaKaoProfile: KaKaoProfile,
    @JsonProperty("phone_number")
    val phoneNumber: String?,
    @JsonProperty("birthyear")
    val birthYear: String?,
    @JsonProperty("birthday")
    val birthDay: String?,
    val gender: String?,
)
