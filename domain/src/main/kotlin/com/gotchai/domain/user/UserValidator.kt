package com.gotchai.domain.user

import com.gotchai.domain.global.exception.ErrorException
import com.gotchai.domain.global.exception.ErrorType
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val userRepository: UserRepository,
) {
    companion object {
        /** 이메일 정규화 **/
        val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}\$")
    }

    fun verifyEmail(email: String) {
        if (!email.matches(EMAIL_REGEX)) {
            throw ErrorException(ErrorType.INVALID_LOGIN_ID_FORMAT)
        }

        if (userRepository.existsByEmail(email)) {
            throw ErrorException(ErrorType.DUPLICATED_USER)
        }
    }
}
