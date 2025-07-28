package com.gotchai.api.presentation.v1.exam

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.exam.response.ExamResponse
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.user.entity.User
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class ExamController(
    private val examQueryUseCase: ExamQueryUseCase,
) {
    @GetMapping("/exams")
    fun getExams(user: User.Issue): List<ExamResponse> = examQueryUseCase.getExams().map { ExamResponse.from(it) }
}
