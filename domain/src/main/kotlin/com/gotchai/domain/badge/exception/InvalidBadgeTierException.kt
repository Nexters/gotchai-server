package com.gotchai.domain.badge.exception

import com.gotchai.domain.global.exception.ServerException

class InvalidBadgeTierException(
    override val message: String = "유효하지 않는 뱃지 티어입니다."
) : ServerException(status = 500, message)
