package com.gotchai.domain.quiz.port.`in`

import com.gotchai.domain.quiz.dto.result.QuizPickResult

interface QuizCommandUseCase {
    fun gradeQuiz(
        examId: Long,
        quizPickId: Long,
        userId: Long
    ): QuizPickResult
}
