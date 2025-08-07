package com.gotchai.domain.exam.exception

import com.gotchai.domain.global.exception.ServerException

class ExamHistoryNotFoundException(
    override val message: String = "테스트 기록이 존재하지 않습니다."
) : ServerException(status = 404, message)
