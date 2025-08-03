package com.gotchai.domain.badge.adapter.`in`

import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.exception.InvalidBadgeTierException
import com.gotchai.domain.badge.port.`in`.BadgeCommandUseCase
import org.springframework.stereotype.Service

@Service
class BadgeCommandService : BadgeCommandUseCase {
    override fun determineTierByCorrectAnswers(correctAnswerCount: Int): Tier =
        when (correctAnswerCount) {
            in 0..2 -> Tier.BRONZE
            in 3..5 -> Tier.SILVER
            in 6..7 -> Tier.GOLD
            else -> throw InvalidBadgeTierException()
        }
}
