package com.gotchai.domain.fixture

import com.gotchai.domain.quiz.entity.Quiz

fun createQuiz(
    id: Long = ID,
    examId: Long = ID,
    contents: String = "테스트 퀴즈 내용",
    order: Int = 1,
    createdAt: java.time.LocalDateTime = CREATED_AT
): Quiz =
    Quiz(
        id = id,
        examId = examId,
        contents = contents,
        order = order,
        createdAt = createdAt
    )
