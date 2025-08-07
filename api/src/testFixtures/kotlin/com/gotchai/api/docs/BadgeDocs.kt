package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.badge.response.BadgeResponse
import com.gotchai.api.util.bodyDesc
import com.gotchai.api.util.fieldsOf
import com.gotchai.api.util.listFieldsOf

val badgeResponseFields =
    fieldsOf(
        BadgeResponse::id bodyDesc "식별자",
        BadgeResponse::examId bodyDesc "테스트 식별자",
        BadgeResponse::name bodyDesc "이름",
        BadgeResponse::description bodyDesc "설명",
        BadgeResponse::image bodyDesc "이미지 URI",
        BadgeResponse::tier bodyDesc "등급",
        BadgeResponse::createdAt bodyDesc "생성 날짜"
    )

val badgeListResponseFields =
    listFieldsOf(
        "list" bodyDesc "뱃지 리스트",
        *badgeResponseFields.toTypedArray()
    )
