package com.gotchai.domain.badge.exception

import com.gotchai.domain.global.exception.ServerException

class BadgeNotFoundException(
    override val message: String = "뱃지를 찾을 수 없습니다."
) : ServerException(status = 404, message)
