package com.gotchai.domain.user.port.`in`

import com.gotchai.domain.user.dto.result.GetUserRankingResult

interface UserQueryUseCase {
    fun getUserRanking(userId: Long): GetUserRankingResult
}
