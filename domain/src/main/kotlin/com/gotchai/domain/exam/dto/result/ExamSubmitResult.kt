package com.gotchai.domain.exam.dto.result

import com.gotchai.domain.badge.entity.Badge

data class ExamSubmitResult(
    val correctAnswerCount: Int,
    val badge: Badge
)
