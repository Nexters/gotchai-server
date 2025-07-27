package com.gotchai.domain.quiz.entity

data class QuizContents(
    val id: Long,
    val quizId: Long,
    val contents: String,
    val type: QuizType,
) {
    data class Creation(
        val quizId: Long,
        val contents: String,
        val type: QuizType,
    )
}
