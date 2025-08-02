package com.gotchai.domain.fixture

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.entity.ExamResult
import java.time.LocalDateTime

const val TITLE = "AI와 크리스마스 파티"
const val SUB_TITLE = "산타는 누구야?"
const val DESCRIPTION_IMAGE = "https://gotchai-dev.s3.ap-northeast-2.amazonaws.com/exam/icon/image"
const val ICON_IMAGE = "https://gotchai-dev.s3.ap-northeast-2.amazonaws.com/exam/description/image"
const val THEME = "blue"
const val ANSWER_COUNT = 1

fun createExam(
    id: Long = ID,
    title: String = TITLE,
    subTitle: String = SUB_TITLE,
    descriptionImage: String = DESCRIPTION_IMAGE,
    iconImage: String = ICON_IMAGE,
    theme: String = THEME,
    createdAt: LocalDateTime = CREATED_AT
): Exam =
    Exam(
        id = id,
        title = title,
        subTitle = subTitle,
        descriptionImage = descriptionImage,
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
    descriptionImage: String = DESCRIPTION_IMAGE,
    iconImage: String = ICON_IMAGE,
    theme: String = THEME,
    quizIds: List<Long> = listOf(ID),
    createdAt: LocalDateTime = CREATED_AT
): GetExamResult =
    GetExamResult(
        id = id,
        title = title,
        subTitle = subTitle,
        descriptionImage = descriptionImage,
        iconImage = iconImage,
        theme = theme,
        quizIds = quizIds,
        createdAt = createdAt
    )
