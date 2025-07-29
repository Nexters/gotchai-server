package com.gotchai.infrastructure.oauth.exception

class IdTokenVerificationException(
    override val message: String = "ID 토큰 인증에 실패했습니다."
) : RuntimeException()
