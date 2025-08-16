package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.GetMyExamResult

data class GetMyExamListResponse(
    val list: List<GetMyExamResponse>
) {
    companion object {
        fun from(results: List<GetMyExamResult>): GetMyExamListResponse = GetMyExamListResponse(results.map { GetMyExamResponse.from(it) })
    }
}
