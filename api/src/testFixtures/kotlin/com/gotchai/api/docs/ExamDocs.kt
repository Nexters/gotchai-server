package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.exam.response.ExamResponse
import com.gotchai.api.presentation.v1.exam.response.GetExamParticipantCountResponse
import com.gotchai.api.presentation.v1.exam.response.StartExamResponse
import com.gotchai.api.presentation.v1.exam.response.SubmitExamResponse
import com.gotchai.api.util.bodyDesc
import com.gotchai.api.util.fieldsOf
import com.gotchai.api.util.listFieldsOf
import com.gotchai.api.util.objectFieldsOf

val examResponseFields =
    fieldsOf(
        ExamResponse::id bodyDesc "식별자",
        ExamResponse::title bodyDesc "제목",
        ExamResponse::subTitle bodyDesc "부제목",
        ExamResponse::description bodyDesc "테스트 설명",
        ExamResponse::prompt bodyDesc "테스트 프롬프트",
        ExamResponse::backgroundImage bodyDesc "설명 이미지 URI",
        ExamResponse::iconImage bodyDesc "아이콘 이미지 URI",
        ExamResponse::theme bodyDesc "테마",
        ExamResponse::createdAt bodyDesc "생성 날짜"
    )

val examListResponseFields =
    listFieldsOf(
        "list" bodyDesc "테스트 리스트",
        *examResponseFields.toTypedArray()
    )

val getExamParticipantCountResponseFields =
    fieldsOf(
        GetExamParticipantCountResponse::participantCount bodyDesc "참여자 수"
    )

val startExamResponseFields =
    fieldsOf(
        StartExamResponse::quizIds bodyDesc "퀴즈 식별자 리스트"
    )

val submitExamResponseFields =
    fieldsOf(SubmitExamResponse::answerCount bodyDesc "정답 개수") +
        objectFieldsOf("badge" bodyDesc "뱃지", *badgeResponseFields.toTypedArray())
