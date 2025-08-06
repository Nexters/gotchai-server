package com.gotchai.api.presentation.v1.onboarding

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.exam.response.ExamResponse
import com.gotchai.domain.onboarding.port.`in`.OnboardingQueryUseCase
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class OnboardingController(
    private val onboardingQueryUseCase: OnboardingQueryUseCase
) {
    @GetMapping("/onboarding/exams")
    fun getOnboardingExam(): ExamResponse =
        onboardingQueryUseCase
            .getOnboardingExam()
            .let { ExamResponse.from(it) }
}
