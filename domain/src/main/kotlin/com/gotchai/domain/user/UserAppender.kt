package com.gotchai.domain.user

import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository,
) {
    fun append(gotchaiCreation: User.GotchaiCreation) {
        userRepository.save(gotchaiCreation)
    }
}
