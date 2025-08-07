package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.entity.UserBadge
import com.gotchai.domain.badge.exception.BadgeNotFoundException
import com.gotchai.domain.badge.port.out.BadgeQueryPort
import com.gotchai.domain.badge.port.out.UserBadgeCommandPort
import com.gotchai.domain.exam.dto.result.SubmitExamResult
import com.gotchai.domain.exam.exception.ExamHistoryNotFoundException
import com.gotchai.domain.exam.exception.ExamNotSolvedException
import com.gotchai.domain.exam.port.`in`.ExamCommandUseCase
import com.gotchai.domain.exam.port.out.ExamHistoryCommandPort
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.quiz.port.out.QuizHistoryQueryPort
import org.springframework.stereotype.Service

@Service
class ExamCommandService(
    private val examHistoryQueryPort: ExamHistoryQueryPort,
    private val examHistoryCommandPort: ExamHistoryCommandPort,
    private val quizHistoryQueryPort: QuizHistoryQueryPort,
    private val userBadgeCommandPort: UserBadgeCommandPort,
    private val badgeQueryPort: BadgeQueryPort
) : ExamCommandUseCase {
    override fun submitExam(
        userId: Long,
        id: Long
    ): SubmitExamResult {
        val examHistory =
            examHistoryQueryPort.getExamHistoryByExamIdAndUserId(id, userId)
                ?: throw ExamHistoryNotFoundException()
        val quizzes = quizHistoryQueryPort.getQuizHistoriesByExamHistoryId(examHistory.id)

        if (examHistory.quizIds.count() != quizzes.count()) throw ExamNotSolvedException()

        examHistoryCommandPort.updateExamHistory(examHistory.copy(isSolved = true))

        val tier = Tier.fromCorrectAnswerCount(examHistory.correctAnswerCount)
        val badge = badgeQueryPort.getBadgeByExamIdAndTier(id, tier) ?: throw BadgeNotFoundException()

        userBadgeCommandPort.createUserBadge(
            UserBadge.Creation(
                userId = userId,
                badgeId = badge.id
            )
        )

        return SubmitExamResult(
            correctAnswerCount = examHistory.correctAnswerCount,
            badge = badge
        )
    }
}
