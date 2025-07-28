package com.gotchai.domain.user.exception

import com.gotchai.domain.global.exception.ServerException

class UserNotFoundException(
    override val message: String = "사용자를 찾을 수 없습니다.",
) : ServerException(status = 404, message)
