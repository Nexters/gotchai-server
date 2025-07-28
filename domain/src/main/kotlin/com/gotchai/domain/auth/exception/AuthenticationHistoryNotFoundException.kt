package com.gotchai.domain.auth.exception

import com.gotchai.domain.global.exception.ServerException

class AuthenticationHistoryNotFoundException(
    override val message: String = "존재하지 않는 인증 기록입니다.",
) : ServerException(status = 404, message)
