package com.gotchai.api.docs

import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.global.dto.ErrorResponse
import com.gotchai.api.util.bodyDesc

val apiResponseFields =
    listOf(
        ApiResponse<*>::isSuccess bodyDesc "처리 성공 여부",
        ApiResponse<*>::status bodyDesc "상태 코드",
        ApiResponse<*>::timestamp bodyDesc "타임 스탬프",
    )

val errorResponseFields =
    listOf(
        ErrorResponse::errorCode bodyDesc "에러 코드",
        ErrorResponse::message bodyDesc "에러 메세지",
    )
