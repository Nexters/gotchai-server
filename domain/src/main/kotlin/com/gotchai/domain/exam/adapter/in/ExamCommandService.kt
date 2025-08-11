package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.entity.UserBadge
import com.gotchai.domain.badge.exception.BadgeNotFoundException
import com.gotchai.domain.badge.port.out.BadgeQueryPort
import com.gotchai.domain.badge.port.out.UserBadgeCommandPort
import com.gotchai.domain.exam.dto.result.StartExamResult
import com.gotchai.domain.exam.dto.result.SubmitExamResult
import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.domain.exam.exception.ExamAlreadySolvedException
import com.gotchai.domain.exam.exception.ExamHistoryNotFoundException
import com.gotchai.domain.exam.exception.ExamNotFoundException
import com.gotchai.domain.exam.exception.ExamNotSolvedException
import com.gotchai.domain.exam.port.`in`.ExamCommandUseCase
import com.gotchai.domain.exam.port.out.ExamHistoryCommandPort
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.domain.quiz.port.out.QuizHistoryQueryPort
import com.gotchai.domain.quiz.port.out.QuizQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExamCommandService(
    private val examQueryPort: ExamQueryPort,
    private val quizQueryPort: QuizQueryPort,
    private val examHistoryQueryPort: ExamHistoryQueryPort,
    private val examHistoryCommandPort: ExamHistoryCommandPort,
    private val quizHistoryQueryPort: QuizHistoryQueryPort,
    private val userBadgeCommandPort: UserBadgeCommandPort,
    private val badgeQueryPort: BadgeQueryPort,
) : ExamCommandUseCase {
    @Transactional
    override fun startExam(
        userId: Long,
        examId: Long
    ): StartExamResult {
        val exam = examQueryPort.getExamById(examId) ?: throw ExamNotFoundException()
        val examHistory = examHistoryQueryPort.getExamHistoryByExamIdAndUserId(exam.id, userId)
        val quizIds =
            quizQueryPort
                .getQuizzesByExamId(exam.id)
                .map { it.id }

        if (examHistory == null) {
            examHistoryCommandPort.createExamHistory(
                ExamHistory.Creation(
                    examId = exam.id,
                    userId = userId,
                    quizIds = quizIds
                )
            )
        } else {
            if (examHistory.isSolved) throw ExamAlreadySolvedException()

            quizHistoryQueryPort.deleteQuizHistoriesByExamHistoryId(examHistory.id)
            examHistoryCommandPort.updateExamHistory(
                examHistory.copy(
                    quizIds = quizIds,
                    correctAnswerCount = 0,
                    isSolved = false
                )
            )
        }

        //        val quizIds =
        //            if (examHistory == null) {
        //                quizQueryPort.getQuizzesByExamId(id)
        //                    .map { it.id }
        //                    .also {
        //                        examHistoryCommandPort.createExamHistory(
        //                            ExamHistory.Creation(
        //                                examId = id,
        //                                userId = userId,
        //                                quizIds = it
        //                            )
        //                        )
        //                    }
        //            } else {
        //                quizHistoryQueryPort.getQuizHistoriesByExamHistoryId(examHistory.id)
        //                    .map { it.quizId }
        //                    .let { quizIds ->
        //                        examHistory.quizIds.filterNot { it in quizIds }
        //                    }
        //            }

        return StartExamResult(quizIds = quizIds)
    }

    @Transactional
    override fun submitExam(
        userId: Long,
        examId: Long
    ): SubmitExamResult {
        val exam = examQueryPort.getExamById(examId) ?: throw ExamNotFoundException()
        val examHistory =
            examHistoryQueryPort.getExamHistoryByExamIdAndUserId(exam.id, userId)
                ?: throw ExamHistoryNotFoundException()
        val quizHistories = quizHistoryQueryPort.getQuizHistoriesByExamHistoryId(examHistory.id)

        if (examHistory.quizIds.count() != quizHistories.count()) throw ExamNotSolvedException()

        examHistoryCommandPort.updateExamHistory(examHistory.copy(isSolved = true))

        val tier = Tier.fromCorrectAnswerCount(examHistory.correctAnswerCount)
        val badge = badgeQueryPort.getBadgeByExamIdAndTier(exam.id, tier) ?: throw BadgeNotFoundException()

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
