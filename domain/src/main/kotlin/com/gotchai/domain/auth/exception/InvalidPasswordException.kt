package com.gotchai.domain.auth.exception

import com.gotchai.domain.global.exception.ServerException

class InvalidPasswordException(
    override val message: String = "비밀번호가 올바르지 않습니다."
) : ServerException(status = 401, message)
