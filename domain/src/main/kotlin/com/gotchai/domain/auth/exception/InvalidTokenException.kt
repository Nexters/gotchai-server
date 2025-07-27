package com.gotchai.domain.auth.exception

import com.gotchai.domain.global.exception.ServerException

class InvalidTokenException(
    override val message: String = "유효하지 않은 토큰입니다.",
) : ServerException(statusCode = 401, message)
