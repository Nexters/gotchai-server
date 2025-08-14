package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.quiz.request.GradeQuizRequest
import com.gotchai.api.presentation.v1.quiz.response.GradeQuizResponse
import com.gotchai.api.presentation.v1.quiz.response.QuizDetailResponse
import com.gotchai.api.presentation.v1.quiz.response.QuizPickResponse
import com.gotchai.api.util.desc
import com.gotchai.api.util.fieldsOf
import com.gotchai.api.util.listFieldsOf

val quizDetailResponseFields =
    fieldsOf(
        QuizDetailResponse::id desc "퀴즈 식별자",
        QuizDetailResponse::contents desc "퀴즈 내용",
        QuizDetailResponse::createdAt desc "생성 날짜"
    ) +
        listFieldsOf(
            "quizPicks" desc "퀴즈 선택지 목록",
            QuizPickResponse::id desc "선택지 식별자",
            QuizPickResponse::contents desc "선택지 내용"
        )

val gradeQuizResponseFields =
    fieldsOf(
        GradeQuizResponse::contents desc "퀴즈 선택지 내용",
        GradeQuizResponse::isAnswer desc "정답 여부",
        GradeQuizResponse::isTimeout desc "시간 초과 여부"
    )

val gradeQuizRequestFields =
    fieldsOf(
        GradeQuizRequest::quizPickId desc "퀴즈 선택지 식별자",
        GradeQuizRequest::isTimeout desc "시간 초과 여부"
    )
