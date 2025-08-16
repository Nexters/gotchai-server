package com.gotchai.domain.admin.exception

import com.gotchai.domain.global.exception.ServerException

class InvalidFileException(
    override val message: String = "유효하지 않은 파일입니다."
) : ServerException(status = 400, message)
