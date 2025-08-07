package com.gotchai.api.presentation.v1.quiz

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.errorResponseFields
import com.gotchai.api.docs.gradeQuizRequestFields
import com.gotchai.api.docs.gradeQuizResponseFields
import com.gotchai.api.docs.quizDetailResponseFields
import com.gotchai.api.fixture.createGradeQuizRequest
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.quiz.response.GradeQuizResponse
import com.gotchai.api.presentation.v1.quiz.response.QuizDetailResponse
import com.gotchai.api.util.document
import com.gotchai.api.util.expectError
import com.gotchai.api.util.paramDesc
import com.gotchai.domain.exam.exception.ExamAlreadySolvedException
import com.gotchai.domain.exam.exception.ExamHistoryNotFoundException
import com.gotchai.domain.fixture.ID
import com.gotchai.domain.fixture.createGetQuizResult
import com.gotchai.domain.fixture.createQuizPick
import com.gotchai.domain.quiz.exception.InvalidQuizPickException
import com.gotchai.domain.quiz.exception.QuizNotFoundException
import com.gotchai.domain.quiz.exception.QuizPickNotFoundException
import com.gotchai.domain.quiz.port.`in`.QuizCommandUseCase
import com.gotchai.domain.quiz.port.`in`.QuizQueryUseCase
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(QuizController::class)
class QuizControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var quizQueryUseCase: QuizQueryUseCase

    @MockkBean
    private lateinit var quizCommandUseCase: QuizCommandUseCase

    init {
        describe("getQuizById()는") {
            context("조회하려는 퀴즈가 존재하는 경우") {
                every { quizQueryUseCase.getQuizById(ID) } returns createGetQuizResult()

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
                every { quizQueryUseCase.getQuizById(any()) } throws QuizNotFoundException()

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

        describe("gradeQuiz()는") {
            context("유효한 퀴즈 채점을 요청하면") {
                val request =
                    createGradeQuizRequest()
                        .also {
                            every { quizCommandUseCase.gradeQuiz(ID, it.toCommand()) } returns createQuizPick()
                        }

                it("상태 코드 200과 GradeQuizResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/quizzes/grade")
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<GradeQuizResponse>>()
                        .document("퀴즈 채점 성공(200)") {
                            requestBody(gradeQuizRequestFields)
                            responseBody(gradeQuizResponseFields)
                        }
                }
            }

            context("시작하지 않은 테스트에 채점을 요청하면") {
                val request =
                    createGradeQuizRequest()
                        .also {
                            every { quizCommandUseCase.gradeQuiz(ID, it.toCommand()) } throws
                                ExamHistoryNotFoundException()
                        }

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/quizzes/grade")
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("퀴즈 채점 실패(404 - 1)") {
                            requestBody(gradeQuizRequestFields)
                            responseBody(errorResponseFields)
                        }
                }
            }

            context("존재하지 않는 퀴즈 선택지를 제출하면") {
                val request =
                    createGradeQuizRequest()
                        .also {
                            every { quizCommandUseCase.gradeQuiz(ID, it.toCommand()) } throws
                                QuizPickNotFoundException()
                        }

                it("상태 코드 404와 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/quizzes/grade")
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("퀴즈 채점 실패(404 - 2)") {
                            requestBody(gradeQuizRequestFields)
                            responseBody(errorResponseFields)
                        }
                }
            }

            context("이미 푼 테스트에 채점을 요청하면") {
                val request =
                    createGradeQuizRequest()
                        .also {
                            every { quizCommandUseCase.gradeQuiz(ID, it.toCommand()) } throws
                                ExamAlreadySolvedException()
                        }

                it("상태 코드 400과 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/quizzes/grade")
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isBadRequest
                        .expectError()
                        .document("퀴즈 채점 실패(400 - 1)") {
                            requestBody(gradeQuizRequestFields)
                            responseBody(errorResponseFields)
                        }
                }
            }

            context("해당 퀴즈에 맞지 않는 퀴즈 선택지를 제출하면") {
                val request =
                    createGradeQuizRequest()
                        .also {
                            every { quizCommandUseCase.gradeQuiz(ID, it.toCommand()) } throws InvalidQuizPickException()
                        }

                it("상태 코드 400과 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/quizzes/grade")
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isBadRequest
                        .expectError()
                        .document("퀴즈 채점 실패(400 - 2)") {
                            requestBody(gradeQuizRequestFields)
                            responseBody(errorResponseFields)
                        }
                }
            }
        }
    }
}
