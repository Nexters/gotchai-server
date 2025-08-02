package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.exam.response.ExamDetailResponse
import com.gotchai.api.presentation.v1.exam.response.ExamResponse
import com.gotchai.api.presentation.v1.exam.response.GetExamParticipantCountResponse
import com.gotchai.api.util.bodyDesc
import com.gotchai.api.util.fieldsOf
import com.gotchai.api.util.listFieldsOf

val examListResponseFields =
    listFieldsOf(
        description = "테스트 리스트",
        ExamResponse::id bodyDesc "식별자",
        ExamResponse::title bodyDesc "제목",
        ExamResponse::subTitle bodyDesc "부제목",
        ExamResponse::descriptionImage bodyDesc "설명 이미지 URI",
        ExamResponse::iconImage bodyDesc "아이콘 이미지 URI",
        ExamResponse::theme bodyDesc "테마",
        ExamResponse::createdAt bodyDesc "생성 날짜"
    )

val examDetailResponseFields =
    fieldsOf(
        ExamDetailResponse::id bodyDesc "식별자",
        ExamDetailResponse::title bodyDesc "제목",
        ExamDetailResponse::subTitle bodyDesc "부제목",
        ExamDetailResponse::descriptionImage bodyDesc "설명 이미지 URI",
        ExamDetailResponse::iconImage bodyDesc "아이콘 이미지 URI",
        ExamDetailResponse::theme bodyDesc "테마",
        ExamDetailResponse::quizIds bodyDesc "퀴즈 식별자 목록",
        ExamDetailResponse::createdAt bodyDesc "생성 날짜"
    )

val getExamParticipantCountResponseFields =
    fieldsOf(
        GetExamParticipantCountResponse::participantCount bodyDesc "참여자 수"
    )
