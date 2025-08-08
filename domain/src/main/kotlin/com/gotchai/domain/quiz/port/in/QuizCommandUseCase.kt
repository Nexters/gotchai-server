package com.gotchai.domain.quiz.port.`in`

import com.gotchai.domain.quiz.dto.command.GradeQuizCommand
import com.gotchai.domain.quiz.dto.result.GradeQuizResult

interface QuizCommandUseCase {
    fun gradeQuiz(
        userId: Long,
        quizId: Long,
        command: GradeQuizCommand
    ): GradeQuizResult
}
