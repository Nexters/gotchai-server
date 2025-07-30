package com.gotchai.domain.auth.exception

import com.gotchai.domain.global.exception.ServerException

class InvalidRefreshTokenException(
    override val message: String = "잘못된 리프레시 토큰입니다."
) : ServerException(status = 401, message)
