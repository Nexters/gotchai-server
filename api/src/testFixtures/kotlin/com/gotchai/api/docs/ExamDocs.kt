package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.exam.response.ExamResponse
import com.gotchai.api.presentation.v1.exam.response.GetExamParticipantCountResponse
import com.gotchai.api.presentation.v1.exam.response.StartExamResponse
import com.gotchai.api.presentation.v1.exam.response.SubmitExamResponse
import com.gotchai.api.util.desc
import com.gotchai.api.util.fieldsOf
import com.gotchai.api.util.listFieldsOf
import com.gotchai.api.util.objectFieldsOf

val examResponseFields =
    fieldsOf(
        ExamResponse::id desc "식별자",
        ExamResponse::title desc "제목",
        ExamResponse::subTitle desc "부제목",
        ExamResponse::description desc "테스트 설명",
        ExamResponse::prompt desc "테스트 프롬프트",
        ExamResponse::backgroundImage desc "배경 이미지 URI",
        ExamResponse::iconImage desc "아이콘 이미지 URI",
        ExamResponse::coverImage desc "커버 이미지 URI",
        ExamResponse::theme desc "테마",
        ExamResponse::isSolved desc "테스트 풀이 여부",
        ExamResponse::createdAt desc "생성 날짜"
    )

val examListResponseFields =
    listFieldsOf(
        "list" desc "테스트 리스트",
        *examResponseFields.toTypedArray()
    )

val getExamParticipantCountResponseFields =
    fieldsOf(
        GetExamParticipantCountResponse::participantCount desc "참여자 수"
    )

val startExamResponseFields =
    fieldsOf(StartExamResponse::quizIds desc "퀴즈 식별자 리스트")

val submitExamResponseFields =
    fieldsOf(SubmitExamResponse::answerCount desc "정답 개수") +
        objectFieldsOf("badge" desc "뱃지", *badgeResponseFields.toTypedArray())
