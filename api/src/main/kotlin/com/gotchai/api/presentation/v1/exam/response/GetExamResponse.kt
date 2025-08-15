package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.GetExamResult

data class GetExamResponse(
    val id: Long,
    val title: String,
    val subTitle: String,
    val iconImage: String,
    val isSolved: Boolean
) {
    companion object {
        fun from(result: GetExamResult): GetExamResponse =
            with(result) {
                GetExamResponse(
                    id = id,
                    title = title,
                    subTitle = subTitle,
                    iconImage = iconImage,
                    isSolved = isSolved
                )
            }
    }
}
