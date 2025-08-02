package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.quiz.response.QuizDetailResponse
import com.gotchai.api.presentation.v1.quiz.response.QuizPickResponse
import com.gotchai.api.util.arrayFieldsOf
import com.gotchai.api.util.bodyDesc
import com.gotchai.api.util.fieldsOf

val quizDetailResponseFields =
    fieldsOf(
        QuizDetailResponse::id bodyDesc "퀴즈 식별자",
        QuizDetailResponse::contents bodyDesc "퀴즈 내용",
        QuizDetailResponse::createdAt bodyDesc "생성 날짜"
    ) +
        arrayFieldsOf(
            "quizPicks",
            "퀴즈 선택지 목록",
            QuizPickResponse::id bodyDesc "선택지 식별자",
            QuizPickResponse::contents bodyDesc "선택지 내용"
        )
