package com.gotchai.api.presentation.v1.exam

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.exam.response.ExamResponse
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.fixture.createExam
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(ExamController::class)
class ExamControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var examQueryUseCase: ExamQueryUseCase

    init {
        describe("getExams()는") {
            context("시험 목록이 존재하는 경우") {
                val exams =
                    listOf(
                        createExam(id = 1L, title = "AI와 크리스마스 파티"),
                        createExam(id = 2L, title = "Gotchai 팀 화이팅!"),
                    )

                every { examQueryUseCase.getExams() } returns exams

                it("상태 코드 200과 ExamResponse 리스트를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/exams")
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<List<ExamResponse>>>()
                }
            }

            context("시험 목록이 비어있는 경우") {
                every { examQueryUseCase.getExams() } returns emptyList()

                it("상태 코드 200과 빈 리스트를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/exams")
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<List<ExamResponse>>>()
                }
            }
        }
    }
}
