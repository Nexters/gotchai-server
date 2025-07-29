package com.gotchai.domain.user.exception

import com.gotchai.domain.global.exception.ServerException

class ProfileNotFoundException(
    override val message: String = "존재하지 않는 프로필입니다."
) : ServerException(status = 404, message)
