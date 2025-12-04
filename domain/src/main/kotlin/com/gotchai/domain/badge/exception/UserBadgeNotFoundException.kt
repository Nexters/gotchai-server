package com.gotchai.domain.badge.exception

import com.gotchai.domain.global.exception.ServerException

class UserBadgeNotFoundException(
    override val message: String = "유저가 취득한 뱃지를 찾을 수 없습니다."
) : ServerException(status = 404, message)
