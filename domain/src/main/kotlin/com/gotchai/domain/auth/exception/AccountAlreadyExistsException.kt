package com.gotchai.domain.auth.exception

import com.gotchai.domain.global.exception.ServerException

class AccountAlreadyExistsException(
    override val message: String = "이미 존재하는 계정입니다."
) : ServerException(status = 409, message)
