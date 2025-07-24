package com.gotchai.infrastructure.oauth.kakao.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class KaKaoProfile(
    val nickname: String?,
)
