package com.gotchai.api.presentation.v1.auth

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.*
import com.gotchai.api.global.dto.ApiResponse
import com.gotchai.api.presentation.v1.auth.request.AuthRequest
import com.gotchai.api.presentation.v1.auth.response.AuthResponse
import com.gotchai.api.util.document
import com.gotchai.api.util.expectError
import com.gotchai.domain.auth.dto.TokenPair
import com.gotchai.domain.auth.exception.InvalidOAuthTokenException
import com.gotchai.domain.auth.exception.InvalidKakaoTokenException
import com.gotchai.domain.auth.exception.InvalidRefreshTokenException
import com.gotchai.domain.auth.port.`in`.AuthCommandUseCase
import com.gotchai.domain.user.entity.SocialProvider
import com.gotchai.infrastructure.oauth.dto.AppleUser
import com.gotchai.infrastructure.oauth.kakao.KaKaoClientResult
import com.gotchai.infrastructure.oauth.port.`in`.OAuthQueryUseCase
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.expectBody

@WebMvcTest(AuthController::class)
class AuthControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var authCommandUseCase: AuthCommandUseCase

    @MockkBean
    private lateinit var oAuthQueryUseCase: OAuthQueryUseCase

    init {
        describe("testLogin()은") {
            context("유효한 테스트 로그인 요청을 받은 경우") {
                val request = AuthRequest.Login("test@example.com", "password")
                val tokenPair = TokenPair("access-token", "refresh-token")

                every { authCommandUseCase.testLogin(request.email, request.password) } returns tokenPair

                it("액세스 토큰과 리프레시 토큰을 반환한다.") {

                    webClient
                        .post()
                        .uri("/api/v1/auth/test-login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<AuthResponse.Token>>()
                        .document("auth-test-login") {
                            requestBody(testLoginRequestFields)
                            responseBody(authTokenResponseFields)
                        }
                }
            }
        }

        describe("testSignUp()은") {
            context("유효한 테스트 회원가입 요청을 받은 경우") {
                it("회원가입 완료 메시지를 반환한다.") {
                    val request = AuthRequest.SignUp("테스트", "test@example.com", "password")

                    every {
                        authCommandUseCase.testSignUp(
                            request.name,
                            request.email,
                            request.password
                        )
                    } returns Unit

                    webClient
                        .post()
                        .uri("/api/v1/auth/test-signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<AuthResponse.Message>>()
                        .document("auth-test-signup") {
                            requestBody(testSignUpRequestFields)
                            responseBody(authMessageResponseFields)
                        }
                }
            }
        }

        describe("kakaoLogin()은") {
            context("유효한 카카오 로그인 요청을 받은 경우") {
                val request = AuthRequest.KakaoLogin("kakao-access-token")
                val kakaoUserInfo = KaKaoClientResult("kakao-id", "test@example.com", "테스트")
                val tokenPair = TokenPair("access-token", "refresh-token")

                every { oAuthQueryUseCase.getKaKaoUserInfo(request.accessToken) } returns kakaoUserInfo
                every {
                    authCommandUseCase.socialLogin(
                        any(),
                        kakaoUserInfo.email,
                        kakaoUserInfo.id,
                        SocialProvider.KAKAO,
                    )
                } returns tokenPair

                it("상태 코드 200과 Token을 반환한다") {
                    webClient
                        .post()
                        .uri("/api/v1/auth/login/kakao")
                        .header("X-DEVICE-ID", "test-device-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<ApiResponse<AuthResponse.Token>>()
                        .document("카카오 로그인 성공(200)") {
                            requestBody(kakaoLoginRequestFields)
                            responseBody(authTokenResponseFields)
                        }
                }
            }

            context("유효하지 않은 카카오 토큰을 받은 경우") {
                it("상태 코드 401과 ErrorResponse를 반환한다") {
                    val request = AuthRequest.KakaoLogin("invalid-token")

                    every { oAuthQueryUseCase.getKaKaoUserInfo(request.accessToken) } throws InvalidKakaoTokenException()

                    webClient
                        .post()
                        .uri("/api/v1/auth/login/kakao")
                        .contentType(MediaType.APPLICATION_JSON)
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

        describe("POST /api/v1/auth/login/apple") {
            context("유효한 Apple 로그인 요청") {
                val request = AuthRequest.AppleLogin("apple-id-token")
                val appleUserInfo = AppleUser("apple-id", "test@example.com")
                val tokenPair = TokenPair("access-token", "refresh-token")

                every { oAuthQueryUseCase.getAppleUserInfo(request.idToken) } returns appleUserInfo
                every {
                    authCommandUseCase.socialLogin(
                        any(),
                        appleUserInfo.email,
                        appleUserInfo.id,
                        SocialProvider.APPLE,
                    )
                } returns
                    tokenPair

                it("액세스 토큰과 리프레시 토큰을 반환한다") {
                    webClient
                        .post()
                        .uri("/api/v1/auth/login/apple")
                        .header("X-DEVICE-ID", "test-device-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<AuthResponse.Token>()
                        .document("auth-apple-login") {
                            requestBody(appleLoginRequestFields)
                            responseBody(authTokenResponseFields)
                        }
                }
            }

            context("유효하지 않은 애플 토큰으로 로그인 시도") {
                it("401 상태 코드를 반환한다") {
                    val request = AuthRequest.AppleLogin("invalid-token")

                    every { oAuthQueryUseCase.getAppleUserInfo(request.idToken) } throws InvalidOAuthTokenException()

                    webClient
                        .post()
                        .uri("/api/v1/auth/login/apple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isUnauthorized
                }
            }
        }

        describe("POST /api/v1/auth/logout") {
            context("유효한 로그아웃 요청") {
                it("로그아웃 완료 메시지를 반환한다") {
                    val request = AuthRequest.Logout("access-token")

                    every { authCommandUseCase.logout(request.accessToken) } returns "1"

                    webClient
                        .post()
                        .uri("/api/v1/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<AuthResponse.Message>()
                        .document("auth-logout") {
                            requestBody(logoutRequestFields)
                            responseBody(authMessageResponseFields)
                        }
                }
            }
        }

        describe("DELETE /api/v1/auth/withdrawal") {
            context("인증된 사용자의 회원탈퇴 요청") {
                it("회원탈퇴 완료 메시지를 반환한다") {
                    every { authCommandUseCase.withdraw(any()) } returns Unit

                    webClient
                        .delete()
                        .uri("/api/v1/auth/withdrawal")
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<AuthResponse.Message>()
                        .document("auth-withdrawal") {
                            responseBody(authMessageResponseFields)
                        }
                }
            }
        }

        describe("POST /api/v1/auth/refresh") {
            context("유효한 토큰 갱신 요청") {
                it("새로운 액세스 토큰과 리프레시 토큰을 반환한다") {
                    val request = AuthRequest.RefreshToken("refresh-token")
                    val tokenPair = TokenPair("new-access-token", "new-refresh-token")

                    every { authCommandUseCase.refresh(request.refreshToken) } returns tokenPair

                    webClient
                        .post()
                        .uri("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<AuthResponse.Token>()
                        .document("auth-refresh-token") {
                            requestBody(refreshTokenRequestFields)
                            responseBody(authTokenResponseFields)
                        }
                }
            }

            context("유효하지 않은 리프레시 토큰으로 갱신 시도") {
                it("401 상태 코드를 반환한다") {
                    val request = AuthRequest.RefreshToken("invalid-refresh-token")

                    every { authCommandUseCase.refresh(request.refreshToken) } throws InvalidRefreshTokenException()

                    webClient
                        .post()
                        .uri("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isUnauthorized
                }
            }
        }
    }
}
