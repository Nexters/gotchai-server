package com.gotchai.domain.user.adapter.`in`

import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.user.dto.result.GetUserRankingResult
import com.gotchai.domain.user.exception.ProfileNotFoundException
import com.gotchai.domain.user.port.`in`.UserQueryUseCase
import com.gotchai.domain.user.port.out.ProfileQueryPort
import org.springframework.stereotype.Service
import kotlin.math.ceil

@Service
class UserQueryService(
    private val profileQueryPort: ProfileQueryPort,
    private val examHistoryQueryPort: ExamHistoryQueryPort
) : UserQueryUseCase {
    override fun getUserRanking(userId: Long): GetUserRankingResult {
        val profile = profileQueryPort.getProfileByUserId(userId) ?: throw ProfileNotFoundException()
        val allSolvedHistories =
            examHistoryQueryPort
                .getAllExamHistoriesWithQuizIds()
                .filter { it.isSolved }

        val rating = calculateUserRating(userId, allSolvedHistories)
        return GetUserRankingResult.of(profile, rating)
    }

    private fun calculateUserRating(
        userId: Long,
        allHistories: List<ExamHistory>
    ): Int {
        if (allHistories.isEmpty() || !allHistories.any { it.userId == userId }) return 100

        val userScores =
            allHistories
                .groupBy { it.userId }
                .mapValues { (_, histories) ->
                    val totalCorrect = histories.sumOf { it.correctAnswerCount }
                    val totalQuizzes = histories.sumOf { it.quizIds.size }
                    if (totalQuizzes == 0) 0.0 else totalCorrect.toDouble() / totalQuizzes.toDouble()
                }

        val userScore = userScores[userId] ?: return 100
        if (userScores.size == 1) return 1

        val usersWithHigherScore = userScores.values.count { it > userScore }

        return when {
            usersWithHigherScore == 0 && userScores.values.all { it == userScore } -> 50
            usersWithHigherScore == 0 -> 1
            else -> {
                val rating = (usersWithHigherScore.toDouble() / userScores.size.toDouble()) * 100
                ceil(rating).toInt().coerceAtLeast(1) // 최소 1% 보장
            }
        }
    }
}
