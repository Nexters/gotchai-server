package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.entity.UserBadge
import com.gotchai.domain.badge.exception.BadgeNotFoundException
import com.gotchai.domain.badge.exception.InvalidBadgeTierException
import com.gotchai.domain.badge.port.out.BadgeQueryPort
import com.gotchai.domain.badge.port.out.UserBadgeCommandPort
import com.gotchai.domain.exam.dto.result.ExamSubmitResult
import com.gotchai.domain.exam.entity.ExamResult
import com.gotchai.domain.exam.exception.ExamHistoryNotFoundException
import com.gotchai.domain.exam.port.`in`.ExamCommandUseCase
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.exam.port.out.ExamResultCommandPort
import org.springframework.stereotype.Service

@Service
class ExamCommandService(
    private val examHistoryQueryPort: ExamHistoryQueryPort,
    private val examResultCommandPort: ExamResultCommandPort,
    private val userBadgeCommandPort: UserBadgeCommandPort,
    private val badgeQueryPort: BadgeQueryPort
) : ExamCommandUseCase {
    override fun submit(
        userId: Long,
        examId: Long
    ): ExamSubmitResult {
        val examHistory =
            examHistoryQueryPort.getHistoryById(
                userId = userId,
                examId = examId
            ) ?: throw ExamHistoryNotFoundException()

        val (takeQuizIds, answerQuizIds, failedQuizIds) =
            examHistory.histories.run {
                Triple(
                    map { it.quizId },
                    filter { it.isAnswer }.map { it.quizId },
                    filter { !it.isAnswer }.map { it.quizId }
                )
            }

        val creation =
            ExamResult.Creation(
                examId = examId,
                userId = userId,
                takeQuizIds = takeQuizIds.joinToString(","),
                answerQuizIds = answerQuizIds.takeIf { it.isNotEmpty() }?.joinToString(","),
                failedQuizIds = failedQuizIds.takeIf { it.isNotEmpty() }?.joinToString(",")
            )

        examResultCommandPort.createExamResult(creation)

        val badgeTier = calculateTierByCorrectAnswers(answerQuizIds.size)
        val badge =
            badgeQueryPort.getBadgeByExamIdAndTier(examId, badgeTier)
                ?: throw BadgeNotFoundException()
        userBadgeCommandPort.createUserBadge(
            UserBadge.Creation(
                userId = userId,
                badgeId = badge.id
            )
        )

        return ExamSubmitResult(
            correctAnswerCount = answerQuizIds.size,
            badge = badge
        )
    }

    private fun calculateTierByCorrectAnswers(correctAnswerCount: Int): Tier =
        when (correctAnswerCount) {
            in 0..2 -> Tier.BRONZE
            in 3..5 -> Tier.SILVER
            in 6..7 -> Tier.GOLD
            else -> throw InvalidBadgeTierException()
        }
}
