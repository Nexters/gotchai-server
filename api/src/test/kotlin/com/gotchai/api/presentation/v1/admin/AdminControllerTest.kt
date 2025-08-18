package com.gotchai.api.presentation.v1.admin

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.createExamRequestFields
import com.gotchai.api.docs.examResponseFields
import com.gotchai.api.fixture.createCreateExamRequest
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.exam.response.ExamResponse
import com.gotchai.api.util.bodyForm
import com.gotchai.api.util.desc
import com.gotchai.api.util.document
import com.gotchai.api.util.expectError
import com.gotchai.domain.admin.port.`in`.AdminCommandUseCase
import com.gotchai.domain.exam.exception.ExamHistoryNotFoundException
import com.gotchai.domain.fixture.ID
import com.gotchai.domain.fixture.createExam
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(AdminController::class)
class AdminControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var adminCommandUseCase: AdminCommandUseCase

    init {
        describe("createExam()은") {
            val request =
                createCreateExamRequest()
                    .also {
                        every { adminCommandUseCase.createExam(any()) } returns createExam()
                    }

            it("상태 코드 200과 ExamResponse를 반환한다.") {
                webClient
                    .post()
                    .uri("/api/v1/admin/exams")
                    .bodyForm(request)
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody<ApiResponse<ExamResponse>>()
                    .document("테스트 생성 성공(200)") {
                        requestForm(createExamRequestFields)
                        responseBody(examResponseFields)
                    }
            }
        }

        describe("deleteExamHistory()는") {
            context("테스트 기록이 존재하는 경우") {
                every { adminCommandUseCase.deleteExamHistoryByExamIdAndUserId(ID, ID) } just runs

                it("상태 코드 200을 반환한다.") {
                    webClient
                        .delete()
                        .uri("/api/v1/admin/users/{userId}/exams/{examId}/histories", ID, ID)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<Void>()
                        .document("테스트 기록 삭제 성공(200)") {
                            pathParams(
                                "userId" desc "유저 식별자",
                                "examId" desc "테스트 식별자"
                            )
                        }
                }
            }

            context("테스트 기록이 존재하지 않는 경우") {
                every { adminCommandUseCase.deleteExamHistoryByExamIdAndUserId(ID, ID) } throws ExamHistoryNotFoundException()

                it("상태 코드 404를 반환한다.") {
                    webClient
                        .delete()
                        .uri("/api/v1/admin/users/{userId}/exams/{examId}/histories", ID, ID)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("테스트 기록 삭제 실패(404)") {
                            pathParams(
                                "userId" desc "유저 식별자",
                                "examId" desc "테스트 식별자"
                            )
                        }
                }
            }
        }
    }
}
