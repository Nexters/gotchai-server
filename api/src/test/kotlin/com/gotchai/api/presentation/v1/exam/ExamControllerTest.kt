package com.gotchai.api.presentation.v1.exam

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.errorResponseFields
import com.gotchai.api.docs.examDetailResponseFields
import com.gotchai.api.docs.examListResponseFields
import com.gotchai.api.docs.getExamParticipantCountResponseFields
import com.gotchai.api.fixture.PARTICIPANT_COUNT
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.exam.response.ExamDetailResponse
import com.gotchai.api.presentation.v1.exam.response.ExamListResponse
import com.gotchai.api.presentation.v1.exam.response.GetExamParticipantCountResponse
import com.gotchai.api.util.document
import com.gotchai.api.util.expectError
import com.gotchai.api.util.paramDesc
import com.gotchai.domain.exam.exception.ExamNotFoundException
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.fixture.ID
import com.gotchai.domain.fixture.createExam
import com.gotchai.domain.fixture.createGetExamResult
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
            context("테스트 목록이 존재하는 경우") {
                val exams =
                    listOf(createExam())
                        .also {
                            every { examQueryUseCase.getExams() } returns it
                        }

                it("상태 코드 200과 ExamListResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/exams")
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<ExamListResponse>>()
                        .document("테스트 리스트 조회 성공(200)") {
                            responseBody(examListResponseFields)
                        }
                }
            }
        }

        describe("getMyExams()는") {
            val exams =
                listOf(createExam())
                    .also {
                        every { examQueryUseCase.getExamsByUserId(ID) } returns it
                    }

            it("상태 코드 200과 ExamListResponse를 반환한다.") {
                webClient
                    .get()
                    .uri("/api/v1/users/me/exam/solved")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody<ApiResponse<ExamListResponse>>()
                    .document("내가 푼 테스트 리스트 조회 성공(200)") {
                        responseBody(examListResponseFields)
                    }
            }
        }

        describe("getExamById()는") {
            context("조회하려는 테스트가 존재하는 경우") {
                val result =
                    createGetExamResult()
                        .also {
                            every { examQueryUseCase.getExamById(ID) } returns it
                        }

                it("상태 코드 200과 ExamDetailResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/exams/{id}", ID)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<ExamDetailResponse>>()
                        .document("테스트 단일 조회 성공(200)") {
                            pathParams("id" paramDesc "테스트 식별자")
                            responseBody(examDetailResponseFields)
                        }
                }
            }

            context("조회하려는 테스트가 존재하지 않는 경우") {
                every { examQueryUseCase.getExamById(any()) } throws ExamNotFoundException()

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/exams/{id}", ID)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("테스트 단일 조회 실패(404)") {
                            pathParams("id" paramDesc "테스트 식별자")
                            responseBody(errorResponseFields)
                        }
                }
            }
        }

        describe("getExamParticipantCount()는") {
            every { examQueryUseCase.getExamParticipantCountById(ID) } returns PARTICIPANT_COUNT

            it("상태 코드 200과 GetExamParticipantCountResponse를 반환한다.") {
                webClient
                    .get()
                    .uri("/api/v1/exams/{id}/participants", ID)
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody<ApiResponse<GetExamParticipantCountResponse>>()
                    .document("테스트 참여자 수 조회 성공(200)") {
                        pathParams("id" paramDesc "테스트 식별자")
                        responseBody(getExamParticipantCountResponseFields)
                    }
            }
        }
    }
}
