package com.gotchai.api.presentation.v1.onboarding

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.errorResponseFields
import com.gotchai.api.docs.examResponseFields
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.exam.response.ExamResponse
import com.gotchai.api.util.document
import com.gotchai.api.util.expectError
import com.gotchai.domain.exam.entity.ExamType
import com.gotchai.domain.exam.exception.ExamNotFoundException
import com.gotchai.domain.fixture.createExam
import com.gotchai.domain.onboarding.port.`in`.OnboardingQueryUseCase
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(OnboardingController::class)
class OnboardingControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var onboardingQueryUseCase: OnboardingQueryUseCase

    init {
        describe("getOnboardingExam()은") {
            context("온보딩 테스트가 존재하는 경우") {
                every { onboardingQueryUseCase.getOnboardingExam() } returns createExam(type = ExamType.ONBOARDING)

                it("상태 코드 200과 ExamResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/onboarding/exams")
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<ExamResponse>>()
                        .document("온보딩 테스트 조회 성공(200)") {
                            responseBody(examResponseFields)
                        }
                }
            }

            context("온보딩 테스트가 존재하지 않는 경우") {
                every { onboardingQueryUseCase.getOnboardingExam() } throws ExamNotFoundException()

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/onboarding/exams")
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("온보딩 테스트 조회 실패(404)") {
                            responseBody(errorResponseFields)
                        }
                }
            }
        }
    }
}
