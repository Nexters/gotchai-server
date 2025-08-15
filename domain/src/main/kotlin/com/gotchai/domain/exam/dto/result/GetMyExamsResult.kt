package com.gotchai.domain.exam.dto.result

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.entity.ExamHistory
import java.time.LocalDateTime

data class GetMyExamsResult(
    val id: Long,
    val title: String,
    val iconImage: String,
    val correctAnswerRate: Int,
    val totalQuizCount: Int,
    val correctAnswerCount: Int,
    val solvedAt: LocalDateTime
) {
    companion object {
        fun of(
            exam: Exam,
            examHistory: ExamHistory
        ): GetMyExamsResult =
            GetMyExamsResult(
                id = exam.id,
                title = exam.title,
                iconImage = exam.iconImage,
                totalQuizCount = examHistory.quizIds.size,
                correctAnswerRate = examHistory.correctAnswerRate,
                correctAnswerCount = examHistory.correctAnswerCount,
                solvedAt = examHistory.createdAt
            )
    }
}
