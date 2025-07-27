package com.gotchai.api.presentation.v1.auth

import com.gotchai.api.common.ControllerTest
import com.gotchai.api.docs.appleLoginRequestFields
import com.gotchai.api.docs.authMessageResponseFields
import com.gotchai.api.docs.authTokenResponseFields
import com.gotchai.api.docs.kakaoLoginRequestFields
import com.gotchai.api.docs.logoutRequestFields
import com.gotchai.api.docs.refreshTokenRequestFields
import com.gotchai.api.docs.testLoginRequestFields
import com.gotchai.api.docs.testSignUpRequestFields
import com.gotchai.api.presentation.v1.auth.request.AuthRequest
import com.gotchai.api.presentation.v1.auth.response.AuthResponse
import com.gotchai.api.util.document
import com.gotchai.domain.auth.dto.TokenPair
import com.gotchai.domain.auth.port.`in`.AuthenticationCommandUseCase
import com.gotchai.domain.user.entity.SocialType
import com.gotchai.infrastructure.oauth.apple.AppleClientResult
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
    private lateinit var authenticationCommandUseCase: AuthenticationCommandUseCase

    @MockkBean
    private lateinit var oAuthQueryUseCase: OAuthQueryUseCase

    init {
        describe("POST /api/v1/auth/test-login") {
            context("유효한 테스트 로그인 요청") {
                it("액세스 토큰과 리프레시 토큰을 반환한다") {
                    val request = AuthRequest.Login("test@example.com", "password")
                    val tokenPair = TokenPair("access-token", "refresh-token")

                    every { authenticationCommandUseCase.testLogin(request.email, request.password) } returns tokenPair

                    // when & then
                    webClient
                        .post()
                        .uri("/api/v1/auth/test-login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<AuthResponse.Token>()
                        .document("auth-test-login") {
                            requestBody(testLoginRequestFields)
                            responseBody(authTokenResponseFields)
                        }
                }
            }
        }

        describe("POST /api/v1/auth/login/kakao") {
            context("유효한 카카오 로그인 요청") {
                it("액세스 토큰과 리프레시 토큰을 반환한다") {
                    val request = AuthRequest.KakaoLogin("kakao-access-token")
                    val kakaoUserInfo = KaKaoClientResult("kakao-id", "test@example.com", "테스트")
                    val tokenPair = TokenPair("access-token", "refresh-token")

                    every { oAuthQueryUseCase.getKaKaoUserInfo(request.accessToken) } returns kakaoUserInfo
                    every {
                        authenticationCommandUseCase.socialLogin(
                            any(),
                            kakaoUserInfo.email,
                            kakaoUserInfo.id,
                            SocialType.KAKAO,
                        )
                    } returns
                        tokenPair

                    webClient
                        .post()
                        .uri("/api/v1/auth/login/kakao")
                        .header("X-DEVICE-ID", "test-device-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<AuthResponse.Token>()
                        .document("auth-kakao-login") {
                            requestBody(kakaoLoginRequestFields)
                            responseBody(authTokenResponseFields)
                        }
                }
            }
        }

        describe("POST /api/v1/auth/test-signup") {
            context("유효한 테스트 회원가입 요청") {
                it("회원가입 완료 메시지를 반환한다") {
                    val request = AuthRequest.SignUp("테스트", "test@example.com", "password")

                    every { authenticationCommandUseCase.testSignUp(request.name, request.email, request.password) } returns Unit

                    webClient
                        .post()
                        .uri("/api/v1/auth/test-signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(request)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody<AuthResponse.Message>()
                        .document("auth-test-signup") {
                            requestBody(testSignUpRequestFields)
                            responseBody(authMessageResponseFields)
                        }
                }
            }
        }

        describe("POST /api/v1/auth/login/apple") {
            context("유효한 Apple 로그인 요청") {
                it("액세스 토큰과 리프레시 토큰을 반환한다") {
                    val request = AuthRequest.AppleLogin("apple-id-token")
                    val appleUserInfo = AppleClientResult("apple-id", "test@example.com")
                    val tokenPair = TokenPair("access-token", "refresh-token")

                    every { oAuthQueryUseCase.getAppleUserInfo(request.idToken) } returns appleUserInfo
                    every {
                        authenticationCommandUseCase.socialLogin(
                            any(),
                            appleUserInfo.email,
                            appleUserInfo.id,
                            SocialType.APPLE,
                        )
                    } returns
                        tokenPair

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
        }

        describe("POST /api/v1/auth/logout") {
            context("유효한 로그아웃 요청") {
                it("로그아웃 완료 메시지를 반환한다") {
                    val request = AuthRequest.Logout("access-token")

                    every { authenticationCommandUseCase.logout(request.accessToken) } returns "1"

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
                    every { authenticationCommandUseCase.withdrawUser(any()) } returns Unit

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

                    every { authenticationCommandUseCase.renew(request.refreshToken) } returns tokenPair

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
        }
    }
}
