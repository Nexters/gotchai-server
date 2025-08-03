package com.gotchai.domain.quiz.dto.result

data class QuizPickResult(
    val contents: String,
    val isAnswer: Boolean,
) {
    companion object {
        fun of(contents: String, isAnswer: Boolean) =
            QuizPickResult(
                contents = contents,
                isAnswer = isAnswer
            )
    }
}
