package com.gotchai.domain.global.exception

class NotFoundDataException(
    override val message: String = "존재하지 않는 데이터입니다.",
) : ServerException(statusCode = 404, message)
