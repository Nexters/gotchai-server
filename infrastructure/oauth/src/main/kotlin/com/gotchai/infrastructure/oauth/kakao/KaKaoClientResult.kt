package com.gotchai.infrastructure.oauth.kakao

data class KaKaoClientResult(
    val id: String,
    val email: String,
    val name: String,
    val nickname: String,
)
