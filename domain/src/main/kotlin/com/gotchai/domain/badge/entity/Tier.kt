package com.gotchai.domain.badge.entity

import com.gotchai.domain.badge.exception.InvalidBadgeTierException

enum class Tier {
    GOLD,
    SILVER,
    BRONZE
    ;

    companion object {
        fun calculateTierByCorrectAnswers(correctAnswerCount: Int): Tier =
            when (correctAnswerCount) {
                in 0..2 -> BRONZE
                in 3..5 -> SILVER
                in 6..7 -> GOLD
                else -> throw InvalidBadgeTierException()
            }
    }
}
