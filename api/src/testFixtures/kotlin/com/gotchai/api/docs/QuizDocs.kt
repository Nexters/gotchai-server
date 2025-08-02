package com.gotchai.api.docs

import com.gotchai.api.presentation.v1.quiz.response.QuizDetailResponse
import com.gotchai.api.util.bodyDesc
import com.gotchai.api.util.fieldsOf

val quizDetailResponseFields =
    fieldsOf(
        QuizDetailResponse::id bodyDesc "식별자",
        QuizDetailResponse::contents bodyDesc "퀴즈 내용",
        QuizDetailResponse::createdAt bodyDesc "생성 날짜",
        "quizPicks[].contents" bodyDesc "선택지 내용",
        "quizPicks[].type" bodyDesc "답안 타입"
    )
