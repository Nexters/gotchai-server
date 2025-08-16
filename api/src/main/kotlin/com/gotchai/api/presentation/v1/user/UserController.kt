package com.gotchai.api.presentation.v1.user

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.user.response.UserRankingResponse
import com.gotchai.domain.user.port.`in`.UserQueryUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class UserController(
    private val userQueryUseCase: UserQueryUseCase
) {
    @GetMapping("/users/ranking")
    fun getUserRanking(
        @AuthenticationPrincipal
        userId: Long
    ): UserRankingResponse = UserRankingResponse.from(userQueryUseCase.getUserRanking(userId))
}
