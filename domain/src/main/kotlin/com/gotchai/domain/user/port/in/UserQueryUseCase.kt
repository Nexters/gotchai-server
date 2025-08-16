package com.gotchai.domain.user.port.`in`

interface UserQueryUseCase {
    fun getUserRanking(userId: Long): Double
}
