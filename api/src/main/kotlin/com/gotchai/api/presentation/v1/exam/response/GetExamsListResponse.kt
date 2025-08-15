package com.gotchai.api.presentation.v1.exam.response

import com.gotchai.domain.exam.dto.result.GetExamsResult

data class GetExamsListResponse(
    val list: List<GetExamsResponse>
) {
    companion object {
        fun from(results: List<GetExamsResult>): GetExamsListResponse =
            GetExamsListResponse(list = results.map { GetExamsResponse.from(it) })
    }
}
