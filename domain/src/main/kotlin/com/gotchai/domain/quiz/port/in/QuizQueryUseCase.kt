package com.gotchai.domain.quiz.port.`in`

import com.gotchai.domain.quiz.dto.result.GetQuizResult

interface QuizQueryUseCase {
    fun getQuizById(id: Long): GetQuizResult
}
