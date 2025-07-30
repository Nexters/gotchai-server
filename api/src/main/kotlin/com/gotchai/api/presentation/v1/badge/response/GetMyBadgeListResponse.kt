package com.gotchai.api.presentation.v1.badge.response

import com.gotchai.domain.badge.dto.result.GetMyBadgeResult

data class GetMyBadgeListResponse(
    val list: List<GetMyBadgeResponse>
) {
    companion object {
        fun from(results: List<GetMyBadgeResult>): GetMyBadgeListResponse =
            GetMyBadgeListResponse(list = results.map { GetMyBadgeResponse.from(it) })
    }
}
