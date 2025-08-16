package com.gotchai.api.presentation.v1.admin

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.admin.request.CreateExamRequest
import com.gotchai.api.presentation.v1.exam.response.ExamResponse
import com.gotchai.domain.admin.port.`in`.AdminCommandUseCase
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@ApiV1Controller
class AdminController(
    private val adminCommandUseCase: AdminCommandUseCase
) {
    @PostMapping("/admin/exams")
    fun createExam(
        @ModelAttribute
        request: CreateExamRequest
    ): ExamResponse =
        adminCommandUseCase
            .createExam(request.toCommand())
            .let { ExamResponse.from(it) }
}
