package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.quiz.request.GradeQuizRequest
import com.gotchai.api.presentation.v1.quiz.response.GradeQuizResponse
import com.gotchai.api.presentation.v1.quiz.response.QuizDetailResponse
import com.gotchai.api.presentation.v1.quiz.response.QuizPickResponse
import com.gotchai.api.util.bodyDesc
import com.gotchai.api.util.fieldsOf
import com.gotchai.api.util.listFieldsOf

val quizDetailResponseFields =
    fieldsOf(
        QuizDetailResponse::id bodyDesc "퀴즈 식별자",
        QuizDetailResponse::contents bodyDesc "퀴즈 내용",
        QuizDetailResponse::createdAt bodyDesc "생성 날짜"
    ) +
        listFieldsOf(
            "quizPicks" bodyDesc "퀴즈 선택지 목록",
            QuizPickResponse::id bodyDesc "선택지 식별자",
            QuizPickResponse::contents bodyDesc "선택지 내용"
        )

val gradeQuizResponseFields =
    fieldsOf(
        GradeQuizResponse::contents bodyDesc "퀴즈 선택지 내용",
        GradeQuizResponse::isAnswer bodyDesc "정답 여부",
        GradeQuizResponse::isTimeout bodyDesc "시간 초과 여부"
    )

val gradeQuizRequestFields =
    fieldsOf(
        GradeQuizRequest::quizPickId bodyDesc "퀴즈 선택지 식별자",
        GradeQuizRequest::isTimeout bodyDesc "시간 초과 여부"
    )
