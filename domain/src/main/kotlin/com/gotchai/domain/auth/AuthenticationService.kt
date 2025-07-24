package com.gotchai.domain.auth

import com.gotchai.domain.global.exception.ErrorException
import com.gotchai.domain.global.exception.ErrorType
import com.gotchai.domain.user.SocialUser
import com.gotchai.domain.user.User
import com.gotchai.domain.user.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val authenticationProcessor: AuthenticationProcessor,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
) {
    fun testLogin(
        email: String,
        password: String,
    ): Token {
        val user =
            userService.getUserCredentialByEmail(email)
                ?: throw ErrorException(ErrorType.NOT_FOUND_USER)

        if (!passwordEncoder.matches(password, user.password)) {
            throw ErrorException(ErrorType.INVALID_PASSWORD)
        }

        return authenticationProcessor.login(User.Issue(user.id))
    }

    fun testSignUp(
        name: String,
        email: String,
        password: String,
    ) {
        userService.testSignUp(name, email, passwordEncoder.encode(password))
    }

    fun socialLogin(
        deviceId: String?,
        socialUser: SocialUser,
    ): Token =
        authenticationProcessor.login(
            deviceId = deviceId,
            socialUser = socialUser,
        )

    fun renew(refreshToken: String): Token = authenticationProcessor.renew(refreshToken)

    fun logout(token: String): String = authenticationProcessor.remove(token)

    fun withdrawUser(userId: Long) {
        authenticationProcessor.withdrawal(userId)
    }
}
