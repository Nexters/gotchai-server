package com.gotchai.domain.quiz.exception

import com.gotchai.domain.global.exception.ServerException

class QuizNotFoundException(
    override val message: String = "존재하지 않는 퀴즈입니다."
) : ServerException(status = 404, message)
