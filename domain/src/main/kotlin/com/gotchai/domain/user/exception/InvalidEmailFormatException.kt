package com.gotchai.domain.user.exception

import com.gotchai.domain.global.exception.ServerException

class InvalidEmailFormatException(
    override val message: String = "이메일 형식이 올바르지 않습니다.",
) : ServerException(status = 400, message)
