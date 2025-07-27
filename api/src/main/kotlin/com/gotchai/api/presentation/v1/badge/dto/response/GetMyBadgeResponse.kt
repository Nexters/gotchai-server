package com.gotchai.api.presentation.v1.badge.dto.response

import com.gotchai.domain.badge.dto.result.GetMyBadgeResult
import com.gotchai.domain.badge.entity.Rank
import java.time.LocalDateTime

data class GetMyBadgeResponse(
    val id: Long,
    val examId: Long,
    val name: String,
    val description: String,
    val image: String,
    val rank: Rank,
    val acquiredAt: LocalDateTime,
) {
    companion object {
        fun from(result: GetMyBadgeResult): GetMyBadgeResponse =
            with(result) {
                GetMyBadgeResponse(
                    id = id,
                    examId = examId,
                    name = name,
                    description = result.description,
                    image = image,
                    rank = rank,
                    acquiredAt = acquiredAt,
                )
            }
    }
}
