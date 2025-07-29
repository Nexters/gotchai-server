package com.gotchai.domain.auth.exception

import com.gotchai.domain.global.exception.ServerException

class InvalidOAuthTokenException(
    override val message: String = "유효하지 않은 OAuth2 토큰입니다."
) : ServerException(status = 401, message)
