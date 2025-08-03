package com.gotchai.domain.badge.port.`in`

import com.gotchai.domain.badge.entity.Tier

interface BadgeCommandUseCase {
    fun determineTierByCorrectAnswers(correctAnswerCount: Int): Tier
}
