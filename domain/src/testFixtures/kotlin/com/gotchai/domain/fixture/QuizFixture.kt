package com.gotchai.domain.fixture

import com.gotchai.domain.quiz.dto.result.GetQuizResult
import com.gotchai.domain.quiz.dto.result.QuizPickResult
import com.gotchai.domain.quiz.entity.AnswerType
import com.gotchai.domain.quiz.entity.Quiz
import com.gotchai.domain.quiz.entity.QuizPick
import java.time.LocalDateTime

const val DEFAULT_QUIZ_CONTENTS = "조선시대랑 좀비 사극이 어떻게 같이 있을 수 있어요? ㅋㅋ"
const val DEFAULT_QUIZ_ORDER = 1
const val AI_PICK_CONTENTS = "배고픈 백성들이 좀비가 되는 설정인데 보다보니까 재밌는 거 같아요 ㅎㅎ"
const val HUMAN_PICK_CONTENTS = "진짜 신선한 조합이죠 ㅋㅋㅋ 전통 한복 입고 궁궐에서 좀비들이 우르르 몰려오는 거예요!"

fun createQuiz(
    id: Long = ID,
    examId: Long = ID,
    contents: String = DEFAULT_QUIZ_CONTENTS,
    order: Int = DEFAULT_QUIZ_ORDER,
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
    contents: String,
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
    contents: String = DEFAULT_QUIZ_CONTENTS,
    createdAt: LocalDateTime = CREATED_AT,
    quizPicks: List<QuizPick> =
        listOf(
            createQuizPick(id = 1L, contents = AI_PICK_CONTENTS, type = AnswerType.AI),
            createQuizPick(id = 2L, contents = HUMAN_PICK_CONTENTS, type = AnswerType.HUMAN)
        )
): GetQuizResult =
    GetQuizResult(
        id = id,
        contents = contents,
        createdAt = createdAt,
        quizPicks = quizPicks
    )

fun createQuizPickResult(
    contents: String = DEFAULT_QUIZ_CONTENTS,
    isAnswer: Boolean = true
): QuizPickResult =
    QuizPickResult(
        contents = contents,
        isAnswer = isAnswer
    )
