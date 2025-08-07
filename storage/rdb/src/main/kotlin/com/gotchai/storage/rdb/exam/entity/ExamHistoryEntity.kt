package com.gotchai.storage.rdb.exam.entity

import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.storage.rdb.global.entity.BaseEntity
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "exam_history")
@Entity
class ExamHistoryEntity(
    val examId: Long,
    val userId: Long,
    @ElementCollection
    val quizIds: List<Long>,
    var correctAnswerCount: Int,
    var isSolved: Boolean
) : BaseEntity() {
    companion object {
        fun from(examHistory: ExamHistory): ExamHistoryEntity =
            with(examHistory) {
                ExamHistoryEntity(
                    examId = examId,
                    userId = userId,
                    quizIds = quizIds,
                    correctAnswerCount = correctAnswerCount,
                    isSolved = isSolved
                )
            }

        fun from(creation: ExamHistory.Creation): ExamHistoryEntity =
            with(creation) {
                ExamHistoryEntity(
                    examId = examId,
                    userId = userId,
                    quizIds = quizIds,
                    correctAnswerCount = correctAnswerCount,
                    isSolved = isSolved
                )
            }
    }

    fun toExamHistory(): ExamHistory =
        ExamHistory(
            id = id!!,
            examId = examId,
            userId = userId,
            quizIds = quizIds,
            correctAnswerCount = correctAnswerCount,
            isSolved = isSolved,
            createdAt = createdAt
        )

    fun update(
        correctAnswerCount: Int,
        isSolved: Boolean
    ) {
        this.correctAnswerCount = correctAnswerCount
        this.isSolved = isSolved
    }
}
