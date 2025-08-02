package com.gotchai.domain.quiz.port.`in`

interface QuizCommandUseCase {
    fun scoreQuiz(quizId: Long, userId: Long): Boolean
}
