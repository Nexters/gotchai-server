package com.gotchai.api.presentation.v1.badge

import com.gotchai.api.global.annotation.ApiV1Controller
import com.gotchai.api.presentation.v1.badge.response.BadgeListResponse
import com.gotchai.api.presentation.v1.badge.response.BadgeResponse
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
        @PathVariable("id")
        badgeId: Long
    ): BadgeResponse =
        badgeQueryUseCase
            .getBadgeById(badgeId)
            .let { BadgeResponse.from(it) }

    @GetMapping("/users/me/badges")
    fun getMyBadges(
        @AuthenticationPrincipal
        userId: Long
    ): BadgeListResponse =
        badgeQueryUseCase
            .getMyBadges(userId)
            .let { BadgeListResponse.from(it) }
}
