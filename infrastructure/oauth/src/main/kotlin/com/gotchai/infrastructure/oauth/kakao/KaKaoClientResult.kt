package com.gotchai.infrastructure.oauth.kakao

data class KaKaoClientResult(
    val id: String,
    val email: String,
    val nickname: String?,
)
