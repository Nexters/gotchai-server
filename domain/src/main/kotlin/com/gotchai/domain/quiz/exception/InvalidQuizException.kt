package com.gotchai.domain.quiz.exception

import com.gotchai.domain.global.exception.ServerException

class InvalidQuizException(
    override val message: String = "유효하지 않은 퀴즈입니다."
) : ServerException(status = 400, message)
