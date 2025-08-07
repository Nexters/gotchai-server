package com.gotchai.domain.exam.exception

import com.gotchai.domain.global.exception.ServerException

class ExamAlreadySolvedException(
    override val message: String = "이미 푼 테스트입니다."
) : ServerException(status = 400, message)
