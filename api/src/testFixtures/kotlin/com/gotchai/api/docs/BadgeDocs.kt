package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.badge.response.BadgeResponse
import com.gotchai.api.util.desc
import com.gotchai.api.util.fieldsOf
import com.gotchai.api.util.listFieldsOf

val badgeResponseFields =
    fieldsOf(
        BadgeResponse::id desc "식별자",
        BadgeResponse::examId desc "테스트 식별자",
        BadgeResponse::name desc "이름",
        BadgeResponse::description desc "설명",
        BadgeResponse::image desc "이미지 URI",
        BadgeResponse::tier desc "등급",
        BadgeResponse::createdAt desc "생성 날짜"
    )

val badgeListResponseFields =
    listFieldsOf(
        "list" desc "뱃지 리스트",
        *badgeResponseFields.toTypedArray()
    )

val getMyBadgeResponseFields =
    fieldsOf(
        *badgeResponseFields.toTypedArray(),
        "acquiredAt" desc "취득 날짜"
    )

val getMyBadgeListResponseFields =
    listFieldsOf(
        "list" desc "내가 취득한 뱃지 리스트",
        *getMyBadgeResponseFields.toTypedArray()
    )
