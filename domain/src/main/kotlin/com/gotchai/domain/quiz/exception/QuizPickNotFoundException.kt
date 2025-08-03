package com.gotchai.domain.quiz.exception

import com.gotchai.domain.global.exception.ServerException

class QuizPickNotFoundException(
    override val message: String = "퀴즈 선택지를 찾을 수 없습니다."
) : ServerException(status = 404, message)
