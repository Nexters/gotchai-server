package com.gotchai.api.presentation.v1.exam

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.exam.response.ExamDetailResponse
import com.gotchai.api.presentation.v1.exam.response.ExamResponse
import com.gotchai.api.util.expectError
import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.fixture.ID
import com.gotchai.domain.fixture.createExam
import com.gotchai.domain.global.exception.NotFoundDataException
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
                        createExam(id = 2L, title = "Gotchai 팀 화이팅!")
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

        describe("getExamById()는") {
            context("조회하려는 시험이 존재하는 경우") {
                val exam = createExam(id = 1L, title = "토익")
                val quizzesIds = listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L)
                val getExamResult = GetExamResult.of(exam, quizzesIds)
                val examDetailResponse = ExamDetailResponse.from(getExamResult)
                every { examQueryUseCase.getExamById(exam.id) } returns getExamResult

                it("상태 코드 200과 ExamDetailResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/exams/{id}", exam.id)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<ExamDetailResponse>>()
                }
            }

            context("조회하려는 시험이 존재하지 않는 경우") {
                every { examQueryUseCase.getExamById(any()) } throws NotFoundDataException()

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/exams/{id}", ID)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                }
            }
        }
    }
}
