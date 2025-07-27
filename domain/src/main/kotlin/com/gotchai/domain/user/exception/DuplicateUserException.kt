package com.gotchai.domain.user.exception

import com.gotchai.domain.global.exception.ServerException

class DuplicateUserException(
    override val message: String = "이미 존재하는 계정입니다.",
) : ServerException(statusCode = 409, message)
