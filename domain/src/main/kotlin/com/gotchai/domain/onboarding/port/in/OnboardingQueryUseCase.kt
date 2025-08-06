package com.gotchai.domain.onboarding.port.`in`

import com.gotchai.domain.exam.entity.Exam

interface OnboardingQueryUseCase {
    fun getOnboardingExam(): Exam
}
