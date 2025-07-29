package com.gotchai.api.presentation.v1.badge

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.badge.response.BadgeResponse
import com.gotchai.api.presentation.v1.badge.response.GetMyBadgeResponse
import com.gotchai.domain.badge.port.`in`.BadgeQueryUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@ApiV1Controller
class BadgeController(
    private val badgeQueryUseCase: BadgeQueryUseCase
) {
    @GetMapping("/badges/{id}")
    fun getBadgeById(
        @PathVariable
        id: Long
    ): BadgeResponse =
        badgeQueryUseCase
            .getBadgeById(id)
            .let { BadgeResponse.from(it) }

    @GetMapping("/users/me/badges")
    fun getMyBadges(
        @AuthenticationPrincipal
        userId: Long
    ): List<GetMyBadgeResponse> =
        badgeQueryUseCase
            .getMyBadges(userId)
            .map { GetMyBadgeResponse.from(it) }
}
