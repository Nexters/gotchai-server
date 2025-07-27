package com.gotchai.domain.auth.exception

import com.gotchai.domain.global.exception.ServerException

class AuthenticationHistoryNotFoundException(
    override val message: String = "인증 기록을 찾을 수 없습니다.",
) : ServerException(statusCode = 404, message)
