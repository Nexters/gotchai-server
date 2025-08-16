package com.gotchai.api.docs

import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.global.dto.ErrorResponse
import com.gotchai.api.util.desc
import com.gotchai.api.util.fieldsOf

val apiResponseFields =
    fieldsOf(
        ApiResponse<*>::isSuccess desc "처리 성공 여부",
        ApiResponse<*>::status desc "상태 코드",
        ApiResponse<*>::data desc "응답 데이터",
        ApiResponse<*>::timestamp desc "타임 스탬프"
    )

val errorResponseFields =
    fieldsOf(
        ErrorResponse::errorCode desc "에러 코드",
        ErrorResponse::message desc "에러 메세지"
    )
