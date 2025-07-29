package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.badge.response.BadgeResponse
import com.gotchai.api.presentation.v1.badge.response.GetMyBadgeResponse
import com.gotchai.api.util.bodyDesc

val badgeResponseFields =
    listOf(
        BadgeResponse::id bodyDesc "식별자",
        BadgeResponse::examId bodyDesc "테스트 식별자",
        BadgeResponse::name bodyDesc "이름",
        BadgeResponse::description bodyDesc "설명",
        BadgeResponse::image bodyDesc "이미지 URI",
        BadgeResponse::tier bodyDesc "등급",
        BadgeResponse::createdAt bodyDesc "생성 날짜"
    )

val getMyBadgeResponseFields =
    listOf(
        GetMyBadgeResponse::id bodyDesc "식별자",
        GetMyBadgeResponse::examId bodyDesc "테스트 식별자",
        GetMyBadgeResponse::name bodyDesc "이름",
        GetMyBadgeResponse::description bodyDesc "설명",
        GetMyBadgeResponse::image bodyDesc "이미지 URI",
        GetMyBadgeResponse::tier bodyDesc "등급",
        GetMyBadgeResponse::acquiredAt bodyDesc "취득 날짜"
    )
