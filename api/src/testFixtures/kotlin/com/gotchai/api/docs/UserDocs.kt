package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.user.response.UserRankingResponse
import com.gotchai.api.util.desc
import com.gotchai.api.util.fieldsOf

val userRankingResponseFields =
    fieldsOf(
        UserRankingResponse::name desc "사용자 이름",
        UserRankingResponse::rating desc "상위 퍼센트 (0-100)"
    )
