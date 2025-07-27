package com.gotchai.domain.auth.exception

import com.gotchai.domain.global.exception.ServerException

class InvalidRefreshTokenException(
    override val message: String = "잘못된 refreshToken 입니다.",
) : ServerException(statusCode = 401, message)
