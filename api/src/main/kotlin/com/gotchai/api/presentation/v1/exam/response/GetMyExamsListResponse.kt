package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.GetMyExamsResult

data class GetMyExamsListResponse(
    val list: List<GetMyExamsResponse>
) {
    companion object {
        fun from(results: List<GetMyExamsResult>): GetMyExamsListResponse =
            GetMyExamsListResponse(results.map { GetMyExamsResponse.from(it) })
    }
}
