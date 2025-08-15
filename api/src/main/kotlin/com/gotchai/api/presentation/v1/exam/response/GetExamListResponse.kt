package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.GetExamResult

data class GetExamListResponse(
    val list: List<GetExamResponse>
) {
    companion object {
        fun from(results: List<GetExamResult>): GetExamListResponse = GetExamListResponse(list = results.map { GetExamResponse.from(it) })
    }
}
