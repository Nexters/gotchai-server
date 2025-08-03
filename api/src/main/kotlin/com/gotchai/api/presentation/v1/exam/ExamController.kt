package com.gotchai.api.presentation.v1.exam

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.exam.response.ExamDetailResponse
import com.gotchai.api.presentation.v1.exam.response.ExamListResponse
import com.gotchai.api.presentation.v1.exam.response.GetExamParticipantCountResponse
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@ApiV1Controller
class ExamController(
    private val examQueryUseCase: ExamQueryUseCase
) {
    @GetMapping("/exams")
    fun getExams(
        @AuthenticationPrincipal
        userId: Long
    ): ExamListResponse = ExamListResponse.from(examQueryUseCase.getExams())

    @GetMapping("/exams/{id}")
    fun getExamById(
        @AuthenticationPrincipal
        userId: Long,
        @PathVariable(name = "id")
        examId: Long
    ): ExamDetailResponse = ExamDetailResponse.from(examQueryUseCase.getExamById(examId))

    @GetMapping("/users/me/exam/solved")
    fun getMyExams(
        @AuthenticationPrincipal
        userId: Long
    ): ExamListResponse =
        examQueryUseCase
            .getExamsByUserId(userId)
            .let { ExamListResponse.from(it) }

    @GetMapping("/exams/{id}/participants")
    fun getExamParticipantCount(
        @PathVariable
        id: Long
    ): GetExamParticipantCountResponse =
        GetExamParticipantCountResponse(participantCount = examQueryUseCase.getExamParticipantCountById(id))
}
