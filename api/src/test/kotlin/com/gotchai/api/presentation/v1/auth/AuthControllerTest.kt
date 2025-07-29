package com.gotchai.api.presentation.v1.auth

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.*
import com.gotchai.api.fixture.createAppleLoginRequest
import com.gotchai.api.fixture.createKakaoLoginRequest
import com.gotchai.api.fixture.createRefreshRequest
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.auth.response.RefreshResponse
import com.gotchai.api.presentation.v1.auth.response.SocialLoginResponse
import com.gotchai.api.util.document
import com.gotchai.api.util.expectError
import com.gotchai.domain.auth.exception.InvalidOAuthTokenException
import com.gotchai.domain.auth.exception.InvalidRefreshTokenException
import com.gotchai.domain.auth.exception.RefreshTokenNotFoundException
import com.gotchai.domain.auth.port.`in`.AuthCommandUseCase
import com.gotchai.domain.fixture.DEVICE_ID
import com.gotchai.domain.fixture.createRefreshResult
import com.gotchai.domain.fixture.createSocialLoginResult
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(AuthController::class)
class AuthControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var authCommandUseCase: AuthCommandUseCase

    init {
        describe("appleLogin()은") {
            val request = createAppleLoginRequest()
            val result = createSocialLoginResult()

            context("유효한 토큰을 받은 경우") {
                every { authCommandUseCase.socialLogin(DEVICE_ID, request.toCommand()) } returns result

                it("상태 코드 200과 SocialLoginResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/auth/login/apple")
                        .header("X-DEVICE-ID", DEVICE_ID)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<SocialLoginResponse>>()
                        .document("애플 로그인 성공(200)") {
                            requestBody(appleLoginRequestFields)
                            responseBody(socialLoginResponseFields)
                        }
                }
            }

            context("유효하지 않은 토큰을 받은 경우") {
                every {
                    authCommandUseCase.socialLogin(DEVICE_ID, request.toCommand())
                } throws InvalidOAuthTokenException()

                it("상태 코드 401과 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/auth/login/apple")
                        .header("X-DEVICE-ID", DEVICE_ID)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isUnauthorized
                        .expectError()
                        .document("애플 로그인 실패(401)") {
                            requestBody(appleLoginRequestFields)
                            responseBody(errorResponseFields)
                        }
                }
            }
        }

        describe("kakaoLogin()은") {
            val request = createKakaoLoginRequest()
            val result = createSocialLoginResult()

            context("유효한 토큰을 받은 경우") {
                every { authCommandUseCase.socialLogin(DEVICE_ID, request.toCommand()) } returns result

                it("상태 코드 200과 SocialLoginResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/auth/login/kakao")
                        .header("X-DEVICE-ID", DEVICE_ID)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<SocialLoginResponse>>()
                        .document("카카오 로그인 성공(200)") {
                            requestBody(kakaoLoginRequestFields)
                            responseBody(socialLoginResponseFields)
                        }
                }
            }

            context("유효하지 않은 토큰을 받은 경우") {
                every {
                    authCommandUseCase.socialLogin(DEVICE_ID, request.toCommand())
                } throws InvalidOAuthTokenException()

                it("상태 코드 401과 ErrorResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/auth/login/kakao")
                        .header("X-DEVICE-ID", DEVICE_ID)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isUnauthorized
                        .expectError()
                        .document("카카오 로그인 실패(401)") {
                            requestBody(kakaoLoginRequestFields)
                            responseBody(errorResponseFields)
                        }
                }
            }
        }

        describe("logout()은") {
            context("로그인한 유저인 경우") {
                every { authCommandUseCase.logout(any()) } just runs

                it("상태 코드 200을 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/auth/logout")
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<Void>()
                        .document("로그아웃 성공(200)")
                }
            }
        }

        describe("refresh()는") {
            val request = createRefreshRequest()
            val result = createRefreshResult()

            context("유효한 리프레시 토큰을 받으면") {
                every { authCommandUseCase.refresh(DEVICE_ID, request.toCommand()) } returns result

                it("상태 코드 200과 RefreshResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/auth/refresh")
                        .header("X-DEVICE-ID", DEVICE_ID)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<RefreshResponse>>()
                        .document("토큰 리프레시 성공(200)") {
                            requestBody(refreshRequestFields)
                            responseBody(refreshResponseFields)
                        }
                }
            }

            context("유효하지 않은 리프레시 토큰을 받으면") {
                every {
                    authCommandUseCase.refresh(DEVICE_ID, request.toCommand())
                } throws InvalidRefreshTokenException()

                it("상태 코드 200과 RefreshResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/auth/refresh")
                        .header("X-DEVICE-ID", DEVICE_ID)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isUnauthorized
                        .expectError()
                        .document("토큰 리프레시 실패(401)") {
                            requestBody(refreshRequestFields)
                            responseBody(errorResponseFields)
                        }
                }
            }

            context("존재하지 않는 리프레시 토큰을 받으면") {
                every {
                    authCommandUseCase.refresh(DEVICE_ID, request.toCommand())
                } throws RefreshTokenNotFoundException()

                it("상태 코드 200과 RefreshResponse를 반환한다.") {
                    webClient
                        .post()
                        .uri("/api/v1/auth/refresh")
                        .header("X-DEVICE-ID", DEVICE_ID)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectError()
                        .document("토큰 리프레시 실패(404)") {
                            requestBody(refreshRequestFields)
                            responseBody(errorResponseFields)
                        }
                }
            }
        }
    }
}
