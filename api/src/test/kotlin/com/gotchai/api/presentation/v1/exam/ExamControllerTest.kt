package com.gotchai.api.presentation.v1.exam

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.*
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.exam.response.*
import com.gotchai.api.util.desc
import com.gotchai.api.util.document
import com.gotchai.api.util.expectError
import com.gotchai.domain.badge.exception.BadgeNotFoundException
import com.gotchai.domain.exam.exception.ExamAlreadySolvedException
import com.gotchai.domain.exam.exception.ExamHistoryNotFoundException
import com.gotchai.domain.exam.exception.ExamNotFoundException
import com.gotchai.domain.exam.port.`in`.ExamCommandUseCase
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.fixture.*
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(ExamController::class)
class ExamControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var examQueryUseCase: ExamQueryUseCase

    @MockkBean
    private lateinit var examCommandUseCase: ExamCommandUseCase

    init {
        describe("getExams(userId)는") {
            every { examQueryUseCase.getExams(ID) } returns listOf(createExamResult())

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

        describe("getMyExams(userId)는") {
            every { examQueryUseCase.getExamsByUserId(ID) } returns listOf(createExamResult())

            it("상태 코드 200과 ExamListResponse를 반환한다.") {
                webClient
                    .get()
                    .uri("/api/v1/users/me/exams/solved")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody<ApiResponse<ExamListResponse>>()
                    .document("내가 푼 테스트 리스트 조회 성공(200)") {
                        responseBody(examListResponseFields)
                    }
            }
        }

        describe("getExamById(examId, userId)는") {
            context("조회하려는 테스트가 존재하는 경우") {
                every { examQueryUseCase.getExamById(ID, ID) } returns createExamResult()

                it("상태 코드 200과 ExamResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/exams/{examId}", ID)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<ExamResponse>>()
                        .document("테스트 단일 조회 성공(200)") {
                            pathParams("examId" desc "테스트 식별자")
                            responseBody(examResponseFields)
                        }
                }
            }

            context("조회하려는 테스트가 존재하지 않는 경우") {
                every { examQueryUseCase.getExamById(any(), any()) } throws ExamNotFoundException()

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/exams/{examId}", ID)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("테스트 단일 조회 실패(404)") {
                            pathParams("examId" desc "테스트 식별자")
                            responseBody(errorResponseFields)
                        }
                }
            }
        }

        describe("getExamParticipantCount(examId)는") {
            every { examQueryUseCase.getExamParticipantCountById(ID) } returns PARTICIPANT_COUNT

            it("상태 코드 200과 GetExamParticipantCountResponse를 반환한다.") {
                webClient
                    .get()
                    .uri("/api/v1/exams/{examId}/participants", ID)
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody<ApiResponse<GetExamParticipantCountResponse>>()
                    .document("테스트 참여자 수 조회 성공(200)") {
                        pathParams("examId" desc "테스트 식별자")
                        responseBody(getExamParticipantCountResponseFields)
                    }
            }
        }

        describe("startExam()은") {
            context("유효한 테스트를 시작하면") {
                every { examCommandUseCase.startExam(ID, ID) } returns createStartExamResult()

                it("상태 코드 200과 StartExamResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/exams/{examId}/start", ID)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<StartExamResponse>>()
                        .document("테스트 시작 성공(200)") {
                            pathParams("examId" desc "테스트 식별자")
                            responseBody(startExamResponseFields)
                        }
                }
            }

            context("이미 푼 테스트를 시작하면") {
                every { examCommandUseCase.startExam(ID, ID) } throws ExamAlreadySolvedException()

                it("상태 코드 400과 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/exams/{examId}/start", ID)
                        .exchange()
                        .expectStatus()
                        .isBadRequest
                        .expectError()
                        .document("테스트 시작 실패(400)") {
                            pathParams("examId" desc "테스트 식별자")
                            responseBody(errorResponseFields)
                        }
                }
            }
        }

        describe("submitExam()는") {
            context("유효한 테스트를 제출하면") {
                every { examCommandUseCase.submitExam(ID, ID) } returns createSubmitExamResult()

                it("상태 코드 200과 SubmitExamResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/exams/{examId}/submit", ID)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<SubmitExamResponse>>()
                        .document("테스트 제출 성공(200)") {
                            pathParams("examId" desc "테스트 식별자")
                            responseBody(submitExamResponseFields)
                        }
                }
            }

            context("시작하지 않은 테스트를 제출하는 경우") {
                every { examCommandUseCase.submitExam(ID, ID) } throws ExamNotFoundException()

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/exams/{examId}/submit", ID)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("테스트 제출 실패(404 - 1)") {
                            pathParams("examId" desc "테스트 식별자")
                            responseBody(errorResponseFields)
                        }
                }
            }

            context("시작하지 않은 테스트를 제출하는 경우") {
                every { examCommandUseCase.submitExam(ID, ID) } throws ExamHistoryNotFoundException()

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/exams/{examId}/submit", ID)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("테스트 제출 실패(404 - 2)") {
                            pathParams("examId" desc "테스트 식별자")
                            responseBody(errorResponseFields)
                        }
                }
            }

            context("이미 푼 테스트를 제출하는 경우") {
                every { examCommandUseCase.submitExam(ID, ID) } throws ExamAlreadySolvedException()

                it("상태 코드 400과 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/exams/{examId}/submit", ID)
                        .exchange()
                        .expectStatus()
                        .isBadRequest
                        .expectError()
                        .document("테스트 제출 실패(400)") {
                            pathParams("examId" desc "테스트 식별자")
                            responseBody(errorResponseFields)
                        }
                }
            }

            context("해당 테스트 결과에 맞는 뱃지가 존재하지 않는 경우") {
                every { examCommandUseCase.submitExam(ID, ID) } throws BadgeNotFoundException()

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/exams/{examId}/submit", ID)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("테스트 제출 실패(404 - 3)") {
                            pathParams("examId" desc "테스트 식별자")
                            responseBody(errorResponseFields)
                        }
                }
            }
        }
    }
}
