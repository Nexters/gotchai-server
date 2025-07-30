package com.gotchai.domain.quiz.entity

data class QuizPick(
    val id: Long,
    val quizId: Long,
    val contents: String,
    val type: AnswerType
) {
    data class Creation(
        val quizId: Long,
        val contents: String,
        val answerType: AnswerType
    )
}
