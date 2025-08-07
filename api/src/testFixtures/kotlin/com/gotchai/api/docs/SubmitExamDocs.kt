package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.exam.response.SubmitExamResponse
import com.gotchai.api.util.bodyDesc
import com.gotchai.api.util.fieldsOf

val submitExamResponseFields =
    fieldsOf(
        SubmitExamResponse::answerCount bodyDesc "정답 개수",
        "badge.id" bodyDesc "배지 식별자",
        "badge.examId" bodyDesc "테스트 식별자",
        "badge.name" bodyDesc "배지 이름",
        "badge.description" bodyDesc "배지 설명",
        "badge.image" bodyDesc "배지 이미지 URI",
        "badge.tier" bodyDesc "배지 등급",
        "badge.createdAt" bodyDesc "배지 생성 날짜"
    )
