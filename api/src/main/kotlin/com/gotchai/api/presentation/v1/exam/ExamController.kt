package com.gotchai.api.presentation.v1.exam

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.exam.response.*
import com.gotchai.domain.exam.port.`in`.ExamCommandUseCase
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@ApiV1Controller
class ExamController(
    private val examQueryUseCase: ExamQueryUseCase,
    private val examCommandUseCase: ExamCommandUseCase
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
        @PathVariable("id")
        examId: Long
    ): ExamResponse = ExamResponse.from(examQueryUseCase.getExamById(examId))

    @GetMapping("/users/me/exams/solved")
    fun getMyExams(
        @AuthenticationPrincipal
        userId: Long
    ): ExamListResponse =
        examQueryUseCase
            .getExamsByUserId(userId)
            .let { ExamListResponse.from(it) }

    @GetMapping("/exams/{id}/participants")
    fun getExamParticipantCountById(
        @PathVariable("id")
        examId: Long
    ): GetExamParticipantCountResponse =
        GetExamParticipantCountResponse(participantCount = examQueryUseCase.getExamParticipantCountById(examId))

    @PostMapping("/exams/{id}/start")
    fun startExam(
        @AuthenticationPrincipal
        userId: Long,
        @PathVariable("id")
        examId: Long
    ): StartExamResponse = StartExamResponse.from(examCommandUseCase.startExam(userId, examId))

    @PostMapping("/exams/{id}/submit")
    fun submitExam(
        @AuthenticationPrincipal
        userId: Long,
        @PathVariable("id")
        examId: Long
    ): SubmitExamResponse = SubmitExamResponse.from(examCommandUseCase.submitExam(userId, examId))
}
