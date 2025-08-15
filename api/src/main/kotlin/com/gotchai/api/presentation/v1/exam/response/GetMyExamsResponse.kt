package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.GetMyExamsResult
import java.time.LocalDateTime

data class GetMyExamsResponse(
    val id: Long,
    val title: String,
    val iconImage: String,
    val correctAnswerRate: Int,
    val totalQuizCount: Int,
    val correctAnswerCount: Int,
    val solvedAt: LocalDateTime
) {
    companion object {
        fun from(result: GetMyExamsResult): GetMyExamsResponse =
            with(result) {
                GetMyExamsResponse(
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
