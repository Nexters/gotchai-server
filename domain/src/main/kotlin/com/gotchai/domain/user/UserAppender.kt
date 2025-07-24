package com.gotchai.domain.user

import org.springframework.stereotype.Component

@Component
class UserAppender(
    private val userRepository: UserRepository,
) {
    fun append(socialCreation: User.SocialCreation): User.Info = userRepository.save(socialCreation)

    fun append(gotchaiCreation: User.GotchaiCreation) = userRepository.save(gotchaiCreation)
}
