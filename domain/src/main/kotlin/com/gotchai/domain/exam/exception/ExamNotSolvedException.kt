package com.gotchai.domain.exam.exception

import com.gotchai.domain.global.exception.ServerException

class ExamNotSolvedException(
    override val message: String = "아직 다 풀지 않은 테스트입니다."
) : ServerException(status = 400, message)
