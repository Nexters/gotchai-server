package com.gotchai.domain.fixture

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.entity.ExamResult
import java.time.LocalDateTime

const val TITLE = "AI와 크리스마스 파티"
const val SUB_TITLE = "산타는 누구야?"
const val DESCRIPTION_EXAM = "때는 2072년...\u2028이제는 AI인지 사람인지 분간이 전혀 가질 않는다.\u2028그런데… 그런 세상 속에서 이상형처럼\u2028딱 맞는 그 사람을 만났다!\n" +
    "설마… 저 사람, AI는 아니겠지?"
const val BACKGROUND_IMAGE = "https://gotchai-dev.s3.ap-northeast-2.amazonaws.com/exam/icon/image"
const val ICON_IMAGE = "https://gotchai-dev.s3.ap-northeast-2.amazonaws.com/exam/description/image"
const val THEME = "blue"
const val ANSWER_COUNT = 1

fun createExam(
    id: Long = ID,
    title: String = TITLE,
    subTitle: String = SUB_TITLE,
    description: String = DESCRIPTION_EXAM,
    backgroundImage: String = BACKGROUND_IMAGE,
    iconImage: String = ICON_IMAGE,
    theme: String = THEME,
    createdAt: LocalDateTime = CREATED_AT
): Exam =
    Exam(
        id = id,
        title = title,
        subTitle = subTitle,
        description = description,
        backgroundImage = backgroundImage,
        iconImage = iconImage,
        theme = theme,
        createdAt = createdAt
    )

fun createExamResult(
    id: Long = ID,
    examId: Long = ID,
    userId: Long = ID,
    answerCount: Int = ANSWER_COUNT,
    createdAt: LocalDateTime = CREATED_AT
): ExamResult =
    ExamResult(
        id = id,
        examId = examId,
        userId = userId,
        answerCount = answerCount,
        createdAt = createdAt
    )

fun createGetExamResult(
    id: Long = ID,
    title: String = TITLE,
    subTitle: String = SUB_TITLE,
    description: String = DESCRIPTION_EXAM,
    backgroundImage: String = BACKGROUND_IMAGE,
    iconImage: String = ICON_IMAGE,
    theme: String = THEME,
    quizIds: List<Long> = listOf(ID),
    createdAt: LocalDateTime = CREATED_AT
): GetExamResult =
    GetExamResult(
        id = id,
        title = title,
        subTitle = subTitle,
        description = description,
        backgroundImage = backgroundImage,
        iconImage = iconImage,
        theme = theme,
        quizIds = quizIds,
        createdAt = createdAt
    )
