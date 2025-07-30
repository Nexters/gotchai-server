package com.gotchai.domain.auth.exception

import com.gotchai.domain.global.exception.ServerException

class UnsupportedLoginMethodException(
    override val message: String = "일반 로그인을 사용할 수 없는 사용자입니다."
) : ServerException(status = 400, message)
