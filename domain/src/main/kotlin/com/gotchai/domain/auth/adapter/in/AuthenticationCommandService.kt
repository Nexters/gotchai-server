package com.gotchai.domain.auth.adapter.`in`

import com.gotchai.domain.auth.dto.Token
import com.gotchai.domain.auth.port.`in`.AuthenticationCommandUseCase
import com.gotchai.domain.global.exception.ErrorException
import com.gotchai.domain.global.exception.ErrorType
import com.gotchai.domain.user.dto.SocialUser
import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.port.`in`.UserCommandUseCase
import com.gotchai.domain.user.port.`in`.UserQueryUseCase
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationCommandService(
    private val authenticationProcessor: AuthenticationProcessor,
    private val userQueryUseCase: UserQueryUseCase,
    private val userCommandUseCase: UserCommandUseCase,
    private val passwordEncoder: PasswordEncoder,
) : AuthenticationCommandUseCase {
    override fun testLogin(
        email: String,
        password: String,
    ): Token {
        val user =
            userQueryUseCase.getUserCredentialByEmail(email)
                ?: throw ErrorException(ErrorType.NOT_FOUND_USER)

        if (!passwordEncoder.matches(password, user.password)) {
            throw ErrorException(ErrorType.INVALID_PASSWORD)
        }

        return authenticationProcessor.login(User.Issue(user.id))
    }

    override fun testSignUp(
        name: String,
        email: String,
        password: String,
    ) {
        userCommandUseCase.testSignUp(name, email, passwordEncoder.encode(password))
    }

    override fun socialLogin(
        deviceId: String?,
        socialUser: SocialUser,
    ): Token =
        authenticationProcessor.login(
            deviceId = deviceId,
            socialUser = socialUser,
        )

    override fun renew(refreshToken: String) = authenticationProcessor.renew(refreshToken)

    override fun logout(token: String) = authenticationProcessor.remove(token)

    override fun withdrawUser(userId: Long) {
        authenticationProcessor.withdrawal(userId)
    }
}
