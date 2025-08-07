package com.gotchai.domain.fixture

import com.gotchai.domain.quiz.dto.result.GetQuizResult
import com.gotchai.domain.quiz.entity.AnswerType
import com.gotchai.domain.quiz.entity.Quiz
import com.gotchai.domain.quiz.entity.QuizPick
import java.time.LocalDateTime

const val CONTENTS = "조선시대랑 좀비 사극이 어떻게 같이 있을 수 있어요? ㅋㅋ"
const val ORDER = 1
val TYPE = AnswerType.AI

fun createQuiz(
    id: Long = ID,
    examId: Long = ID,
    contents: String = CONTENTS,
    order: Int = ORDER,
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
    contents: String = CONTENTS,
    type: AnswerType = TYPE
): QuizPick =
    QuizPick(
        id = id,
        quizId = quizId,
        contents = contents,
        type = type
    )

fun createGetQuizResult(
    id: Long = ID,
    contents: String = CONTENTS,
    createdAt: LocalDateTime = CREATED_AT,
    quizPicks: List<QuizPick> = listOf(createQuizPick())
): GetQuizResult =
    GetQuizResult(
        id = id,
        contents = contents,
        createdAt = createdAt,
        quizPicks = quizPicks
    )
