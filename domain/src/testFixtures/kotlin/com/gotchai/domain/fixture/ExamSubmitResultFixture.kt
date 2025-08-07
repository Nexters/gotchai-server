package com.gotchai.domain.fixture

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.exam.dto.result.ExamSubmitResult

fun createExamSubmitResult(
    correctAnswerCount: Int = 3,
    badge: Badge = createBadge()
): ExamSubmitResult =
    ExamSubmitResult(
        correctAnswerCount = correctAnswerCount,
        badge = badge
    )
