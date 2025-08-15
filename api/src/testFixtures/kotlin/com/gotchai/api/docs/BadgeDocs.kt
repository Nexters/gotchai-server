package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.badge.response.BadgeResponse
import com.gotchai.api.presentation.v1.badge.response.GetMyBadgeResponse
import com.gotchai.api.presentation.v1.badge.response.GetMyBadgesResponse
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
        GetMyBadgeResponse::id desc "식별자",
        GetMyBadgeResponse::name desc "이름",
        GetMyBadgeResponse::image desc "이미지 URI",
        GetMyBadgeResponse::acquiredAt desc "취득 날짜"
    )

val getMyBadgesResponseFields =
    fieldsOf(
        *listFieldsOf(
            GetMyBadgesResponse::badges desc "내가 취득한 뱃지 리스트",
            *getMyBadgeResponseFields.toTypedArray()
        ).toTypedArray(),
        GetMyBadgesResponse::totalBadgeCount desc "총 뱃지 개수"
    )
