package com.gotchai.domain.fixture

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.entity.Exam
import java.time.LocalDateTime

fun createExam(
    id: Long = ID,
    title: String = "테스트 시험",
    subTitle: String = "테스트 부제목",
    descriptionImage: String = "https://example.com/description.jpg",
    iconImage: String = "https://example.com/icon.jpg",
    theme: String = "blue",
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

fun createGetExamResult(
    exam: Exam = createExam(),
    quizIds: List<Long> = listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L)
): GetExamResult = GetExamResult.of(exam, quizIds)
