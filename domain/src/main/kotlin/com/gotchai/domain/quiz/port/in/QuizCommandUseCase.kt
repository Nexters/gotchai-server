package com.gotchai.domain.quiz.port.`in`

import com.gotchai.domain.quiz.dto.command.GradeQuizCommand
import com.gotchai.domain.quiz.entity.QuizPick

interface QuizCommandUseCase {
    fun gradeQuiz(
        userId: Long,
        command: GradeQuizCommand
    ): QuizPick
}
