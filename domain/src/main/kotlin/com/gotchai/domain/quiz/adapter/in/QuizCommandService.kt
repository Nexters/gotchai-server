package com.gotchai.domain.quiz.adapter.`in`

import com.gotchai.domain.exam.port.out.ExamHistoryCommandPort
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.quiz.dto.result.QuizPickResult
import com.gotchai.domain.quiz.entity.QuizHistory
import com.gotchai.domain.quiz.exception.QuizPickNotFoundException
import com.gotchai.domain.quiz.port.`in`.QuizCommandUseCase
import com.gotchai.domain.quiz.port.out.QuizPickQueryPort
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class QuizCommandService(
    private val quizPickQueryPort: QuizPickQueryPort,
    private val examHistoryCommandPort: ExamHistoryCommandPort,
    private val examHistoryQueryPort: ExamHistoryQueryPort
) : QuizCommandUseCase {
    override fun gradeQuiz(
        examId: Long,
        quizPickId: Long,
        userId: Long
    ): QuizPickResult {
        val quizPick =
            quizPickQueryPort.getQuizPickById(quizPickId)
                ?: throw QuizPickNotFoundException()

        val examHistory = QuizHistory.from(userId, examId, quizPick)

        setExamHistory(examHistory, examId)

        return QuizPickResult(quizPick.contents, examHistory.isAnswer)
    }

    private fun setExamHistory(
        quizHistory: QuizHistory,
        examId: Long
    ) {
        val examHistory = examHistoryQueryPort.getHistoryById(quizHistory.examHistoryId)
        val updatedHistories = (examHistory?.histories ?: emptyList()) + quizHistory

        if (examHistory == null) {
            examHistoryCommandPort.create(
                quizHistory.examHistoryId,
                updatedHistories,
                examId,
                LocalDateTime.now(),
                Duration.ofDays(1)
            )
        } else {
            examHistoryCommandPort.updateHistory(quizHistory.examHistoryId, updatedHistories)
        }
    }
}
