package com.gotchai.domain.user.adapter.`in`

import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.user.port.`in`.UserQueryUseCase
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val examHistoryQueryPort: ExamHistoryQueryPort
) : UserQueryUseCase {
    override fun getUserRanking(userId: Long): Double {
        val allHistories = examHistoryQueryPort.getAllExamHistories()

        if (allHistories.isEmpty()) return 100.0
        if (!allHistories.any { it.userId == userId }) return 100.0

        val userScores =
            allHistories
                .groupBy { it.userId }
                .mapValues { (_, histories) ->
                    val totalCorrect = histories.sumOf { it.correctAnswerCount }
                    val totalQuizzes = histories.sumOf { it.quizIds.size }
                    if (totalQuizzes == 0) 0.0 else totalCorrect.toDouble() / totalQuizzes.toDouble()
                }

        val userScore = userScores[userId] ?: return 100.0

        if (userScores.size == 1) return 1.0

        val usersWithHigherScore = userScores.values.count { it > userScore }

        return (usersWithHigherScore.toDouble() / userScores.size.toDouble()) * 100
    }
}
