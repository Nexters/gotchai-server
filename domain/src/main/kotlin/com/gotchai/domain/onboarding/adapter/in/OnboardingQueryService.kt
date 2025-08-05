package com.gotchai.domain.onboarding.adapter.`in`

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.entity.ExamType
import com.gotchai.domain.exam.exception.ExamNotFoundException
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.domain.onboarding.port.`in`.OnboardingQueryUseCase
import org.springframework.stereotype.Service

@Service
class OnboardingQueryService(
    private val examQueryPort: ExamQueryPort
) : OnboardingQueryUseCase {
    override fun getOnboardingExam(): Exam =
        examQueryPort.getExamByType(ExamType.ONBOARDING) ?: throw ExamNotFoundException()
}
