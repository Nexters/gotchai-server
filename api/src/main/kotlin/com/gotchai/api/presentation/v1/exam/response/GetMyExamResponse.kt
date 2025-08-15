package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.GetMyExamResult
import java.time.LocalDateTime

data class GetMyExamResponse(
    val id: Long,
    val title: String,
    val iconImage: String,
    val correctAnswerRate: Int,
    val totalQuizCount: Int,
    val correctAnswerCount: Int,
    val solvedAt: LocalDateTime
) {
    companion object {
        fun from(result: GetMyExamResult): GetMyExamResponse =
            with(result) {
                GetMyExamResponse(
                    id = id,
                    title = title,
                    iconImage = iconImage,
                    correctAnswerRate = correctAnswerRate,
                    totalQuizCount = totalQuizCount,
                    correctAnswerCount = correctAnswerCount,
                    solvedAt = solvedAt
                )
            }
    }
}
