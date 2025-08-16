package com.gotchai.domain.user.adapter.`in`

import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.user.dto.result.GetUserRankingResult
import com.gotchai.domain.user.exception.ProfileNotFoundException
import com.gotchai.domain.user.port.`in`.UserQueryUseCase
import com.gotchai.domain.user.port.out.ProfileQueryPort
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val profileQueryPort: ProfileQueryPort,
    private val examHistoryQueryPort: ExamHistoryQueryPort
) : UserQueryUseCase {
    override fun getUserRanking(userId: Long): GetUserRankingResult {
        val profile = profileQueryPort.getProfileByUserId(userId) ?: throw ProfileNotFoundException()
        val allHistories = examHistoryQueryPort.getAllExamHistories()

        val rating = calculateUserRating(userId, allHistories)
        return GetUserRankingResult.of(profile, rating)
    }

    private fun calculateUserRating(
        userId: Long,
        allHistories: List<ExamHistory>
    ): Double {
        if (allHistories.isEmpty() || !allHistories.any { it.userId == userId }) return 100.0

        val userScores =
            allHistories
                .groupBy { it.userId }
                .mapValues { (_, histories) ->
                    val totalCorrect = histories.sumOf { it.correctAnswerCount }
                    val totalQuizzes = histories.sumOf { it.quizIds.size }
                    if (totalQuizzes == 0) 0.0 else totalCorrect.toDouble() / totalQuizzes.toDouble()
                }

        val userScore = userScores[userId] ?: return 100.0
        if (userScores.size == 1) return 0.0

        val usersWithHigherScore = userScores.values.count { it > userScore }
        return (usersWithHigherScore.toDouble() / userScores.size.toDouble()) * 100
    }
}
