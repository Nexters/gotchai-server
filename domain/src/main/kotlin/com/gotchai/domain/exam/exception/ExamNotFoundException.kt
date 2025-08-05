package com.gotchai.domain.exam.exception

import com.gotchai.domain.global.exception.ServerException

class ExamNotFoundException(
    override val message: String = "존재하지 않는 테스트입니다."
) : ServerException(status = 404, message)
