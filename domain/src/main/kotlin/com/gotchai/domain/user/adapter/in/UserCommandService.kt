package com.gotchai.domain.user.adapter.`in`

import com.gotchai.domain.global.exception.ErrorException
import com.gotchai.domain.global.exception.ErrorType
import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.port.`in`.UserCommandUseCase
import com.gotchai.domain.user.port.out.UserCommandPort
import com.gotchai.domain.user.port.out.UserQueryPort
import org.springframework.stereotype.Service

@Service
class UserCommandService(
    private val userCommandPort: UserCommandPort,
    private val userQueryPort: UserQueryPort,
) : UserCommandUseCase {
    companion object {
        /** 이메일 정규화 **/
        val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}\$")
    }

    override fun create(create: User.SocialCreation): User.Info = userCommandPort.save(create)

    override fun testSignUp(
        name: String,
        email: String,
        password: String,
    ) {
        verifyEmail(email)
        userCommandPort.save(
            User.GotchaiCreation(
                name = name,
                email = email,
                password = password,
            ),
        )
    }

    private fun verifyEmail(email: String) {
        if (!email.matches(EMAIL_REGEX)) {
            throw ErrorException(ErrorType.INVALID_LOGIN_ID_FORMAT)
        }

        if (userQueryPort.existsByEmail(email)) {
            throw ErrorException(ErrorType.DUPLICATED_USER)
        }
    }
}
