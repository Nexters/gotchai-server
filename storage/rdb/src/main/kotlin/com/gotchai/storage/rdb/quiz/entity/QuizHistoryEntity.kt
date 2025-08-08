package com.gotchai.storage.rdb.quiz.entity

import com.gotchai.domain.quiz.entity.QuizHistory
import com.gotchai.storage.rdb.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "quiz_history")
@Entity
class QuizHistoryEntity(
    val examHistoryId: Long,
    val quizId: Long,
    val quizPickId: Long?,
    val isAnswer: Boolean
) : BaseEntity() {
    companion object {
        fun from(creation: QuizHistory.Creation) =
            with(creation) {
                QuizHistoryEntity(
                    examHistoryId = examHistoryId,
                    quizId = quizId,
                    quizPickId = quizPickId,
                    isAnswer = isAnswer
                )
            }
    }

    fun toQuizHistory(): QuizHistory =
        QuizHistory(
            id = id!!,
            examHistoryId = examHistoryId,
            quizId = quizId,
            quizPickId = quizPickId,
            isAnswer = isAnswer,
            createdAt = createdAt
        )
}
