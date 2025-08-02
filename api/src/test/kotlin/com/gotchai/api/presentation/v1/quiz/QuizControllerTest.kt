package com.gotchai.api.presentation.v1.quiz

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.errorResponseFields
import com.gotchai.api.docs.quizDetailResponseFields
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.quiz.response.QuizDetailResponse
import com.gotchai.api.util.document
import com.gotchai.api.util.expectError
import com.gotchai.api.util.paramDesc
import com.gotchai.domain.fixture.ID
import com.gotchai.domain.fixture.createGetQuizResult
import com.gotchai.domain.global.exception.NotFoundDataException
import com.gotchai.domain.quiz.port.`in`.QuizQueryUseCase
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(QuizController::class)
class QuizControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var quizQueryUseCase: QuizQueryUseCase

    init {
        describe("getQuizById()는") {
            context("조회하려는 퀴즈가 존재하는 경우") {
                val result = createGetQuizResult()
                every { quizQueryUseCase.getQuizById(ID) } returns result

                it("상태 코드 200과 QuizDetailResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/quizzes/{id}", ID)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<QuizDetailResponse>>()
                        .document("퀴즈 단일 조회 성공(200)") {
                            pathParams("id" paramDesc "퀴즈 식별자")
                            responseBody(quizDetailResponseFields)
                        }
                }
            }

            context("조회하려는 퀴즈가 존재하지 않는 경우") {
                every { quizQueryUseCase.getQuizById(any()) } throws NotFoundDataException()

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .get()
                        .uri("/api/v1/quizzes/{id}", ID)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("퀴즈 단일 조회 실패(404)") {
                            pathParams("id" paramDesc "퀴즈 식별자")
                            responseBody(errorResponseFields)
                        }
                }
            }
        }
    }
}
