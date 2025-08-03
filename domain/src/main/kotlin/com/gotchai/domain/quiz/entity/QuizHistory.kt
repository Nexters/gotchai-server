package com.gotchai.domain.quiz.entity

data class QuizHistory(
    val examHistoryId: String,
    val quizId: Long,
    val quizPickId: Long,
    val isAnswer: Boolean
) {
    companion object {
        fun from(
            userId: Long,
            examId: Long,
            quizPick: QuizPick
        ): QuizHistory =
            QuizHistory(
                examHistoryId = "exam:$examId:$userId",
                quizPickId = quizPick.id,
                quizId = quizPick.quizId,
                isAnswer = quizPick.type == AnswerType.AI
            )
    }
}
