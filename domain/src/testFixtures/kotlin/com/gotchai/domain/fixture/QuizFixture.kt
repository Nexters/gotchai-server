package com.gotchai.domain.fixture

import com.gotchai.domain.quiz.dto.result.GetQuizResult
import com.gotchai.domain.quiz.entity.AnswerType
import com.gotchai.domain.quiz.entity.Quiz
import com.gotchai.domain.quiz.entity.QuizPick
import java.time.LocalDateTime

fun createQuiz(
    id: Long = ID,
    examId: Long = ID,
    contents: String = "퀴즈 내용",
    order: Int = 1,
    createdAt: LocalDateTime = CREATED_AT
): Quiz =
    Quiz(
        id = id,
        examId = examId,
        contents = contents,
        order = order,
        createdAt = createdAt
    )

fun createQuizPick(
    id: Long = ID,
    quizId: Long = ID,
    contents: String = "퀴즈 선택지",
    type: AnswerType
): QuizPick =
    QuizPick(
        id = id,
        quizId = quizId,
        contents = contents,
        type = type
    )

fun createGetQuizResult(
    id: Long = ID,
    contents: String = "퀴즈 내용",
    createdAt: LocalDateTime = CREATED_AT,
    quizPicks: List<QuizPick> =
        listOf(
            createQuizPick(id = 1L, contents = "AI 선택지", type = AnswerType.AI),
            createQuizPick(id = 2L, contents = "사람 선택", type = AnswerType.HUMAN)
        )
): GetQuizResult =
    GetQuizResult(
        id = id,
        contents = contents,
        createdAt = createdAt,
        quizPicks = quizPicks
    )
