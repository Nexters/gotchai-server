package com.gotchai.domain.auth.adapter.`in`

import com.gotchai.domain.auth.exception.InvalidPasswordException
import com.gotchai.domain.auth.port.out.OAuthQueryPort
import com.gotchai.domain.auth.port.out.RefreshTokenCommandPort
import com.gotchai.domain.auth.port.out.RefreshTokenQueryPort
import com.gotchai.domain.fixture.*
import com.gotchai.domain.global.jwt.JwtProvider
import com.gotchai.domain.user.exception.UserNotFoundException
import com.gotchai.domain.user.port.out.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.Duration

class AuthCommandServiceTest : BehaviorSpec() {
    private val userQueryPort = mockk<UserQueryPort>()
    private val userCommandPort = mockk<UserCommandPort>()
    private val profileQueryPort = mockk<ProfileQueryPort>()
    private val profileCommandPort = mockk<ProfileCommandPort>()
    private val userSocialQueryPort = mockk<UserSocialQueryPort>()
    private val userSocialCommandPort = mockk<UserSocialCommandPort>()
    private val refreshTokenQueryPort = mockk<RefreshTokenQueryPort>()
    private val refreshTokenCommandPort = mockk<RefreshTokenCommandPort>()
    private val oAuthQueryPort = mockk<OAuthQueryPort>()
    private val jwtProvider = mockk<JwtProvider>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val refreshTokenExpiration = Duration.ofHours(1)

    private val authCommandService =
        AuthCommandService(
            userQueryPort = userQueryPort,
            userCommandPort = userCommandPort,
            profileQueryPort = profileQueryPort,
            profileCommandPort = profileCommandPort,
            userSocialQueryPort = userSocialQueryPort,
            userSocialCommandPort = userSocialCommandPort,
            refreshTokenQueryPort = refreshTokenQueryPort,
            refreshTokenCommandPort = refreshTokenCommandPort,
            oAuthQueryPort = oAuthQueryPort,
            jwtProvider = jwtProvider,
            passwordEncoder = passwordEncoder,
            refreshTokenExpiration = refreshTokenExpiration
        )

    override suspend fun beforeSpec(spec: Spec) {
        every { jwtProvider.createAccessToken(any()) } returns TOKEN
        every { jwtProvider.createRefreshToken(any()) } returns TOKEN
    }

    init {
        Given("일반 로그인으로 가입한 유저의 경우") {
            val user =
                createUser()
                    .also {
                        every { userQueryPort.getUserByEmail(it.email) } returns it
                    }
            val refreshToken =
                createRefreshToken()
                    .also {
                        every { refreshTokenCommandPort.createRefreshToken(any()) } returns it
                    }

            And("올바른 비밀번호를 입력하고") {
                every { passwordEncoder.matches(any(), user.password) } returns true

                When("일반 로그인을 시도하면") {
                    val command = createLoginCommand()
                    val result = authCommandService.login(DEVICE_ID, command)

                    Then("액세스 토큰과 리프레시 토큰이 발급된다") {
                        result shouldBe createLoginResult()
                    }
                }
            }

            And("틀린 비밀번호를 입력하고") {
                val invalidPassword = "invalid_password"

                every { passwordEncoder.matches(invalidPassword, user.password) } returns false

                When("일반 로그인을 시도하면") {
                    val command = createLoginCommand(password = invalidPassword)

                    Then("예외가 발생한다") {
                        shouldThrow<InvalidPasswordException> {
                            authCommandService.login(DEVICE_ID, command)
                        }
                    }
                }
            }
        }

        Given("가입하지 않은 유저의 경우") {
            every { userQueryPort.getUserByEmail(EMAIL) } returns null

            When("일반 로그인을 시도하면") {
                val command = createLoginCommand()

                Then("예외가 발생한다") {
                    shouldThrow<UserNotFoundException> {
                        authCommandService.login(DEVICE_ID, command)
                    }
                }
            }
        }

        Given("소셜 로그인으로 가입한 유저의 경우") {
            val user =
                createUser(password = null)
                    .also {
                        every { userQueryPort.getUserById(it.id) } returns it
                    }
            val userSocial =
                createUserSocial()
                    .also {
                        every { userSocialQueryPort.getUserSocialBySocialId(it.socialId) } returns it
                    }
            val oAuthUser =
                createOAuthUser()
                    .also {
                        every { oAuthQueryPort.getOAuthUserByToken(any(), it.provider) } returns it
                    }
            val refreshToken =
                createRefreshToken()
                    .also {
                        every { refreshTokenCommandPort.createRefreshToken(any()) } returns it
                    }

            When("소셜 로그인을 시도하면") {
                val command = createSocialLoginCommand()
                val result = authCommandService.socialLogin(DEVICE_ID, command)

                Then("액세스 토큰과 리프레시 토큰이 발급된다.") {
                    result shouldBe createSocialLoginResult()
                }
            }
        }
    }
}
