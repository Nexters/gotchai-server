package com.gotchai.domain.quiz.adapter.`in`

import com.gotchai.domain.exam.exception.*
import com.gotchai.domain.exam.port.out.*
import com.gotchai.domain.quiz.dto.command.GradeQuizCommand
import com.gotchai.domain.quiz.dto.result.GradeQuizResult
import com.gotchai.domain.quiz.entity.AnswerType
import com.gotchai.domain.quiz.entity.QuizHistory
import com.gotchai.domain.quiz.exception.*
import com.gotchai.domain.quiz.port.`in`.QuizCommandUseCase
import com.gotchai.domain.quiz.port.out.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuizCommandService(
    private val quizQueryPort: QuizQueryPort,
    private val quizPickQueryPort: QuizPickQueryPort,
    private val quizHistoryCommandPort: QuizHistoryCommandPort,
    private val examHistoryQueryPort: ExamHistoryQueryPort,
    private val examHistoryCommandPort: ExamHistoryCommandPort
) : QuizCommandUseCase {
    @Transactional
    override fun gradeQuiz(
        userId: Long,
        quizId: Long,
        command: GradeQuizCommand
    ): GradeQuizResult {
        with(command) {
            val quiz = quizQueryPort.getQuizById(quizId) ?: throw QuizNotFoundException()
            val examHistory =
                examHistoryQueryPort.getExamHistoryByExamIdAndUserId(quiz.examId, userId)
                    ?: throw ExamHistoryNotFoundException()
            val quizPicks = quizPickQueryPort.getQuizPicksByQuizId(quizId)
            val answerPick = quizPicks.first { it.type == AnswerType.AI }
            val isAnswer = answerPick.id == quizPickId && !isTimeout

            if (examHistory.isSolved) throw ExamAlreadySolvedException()
            if (quiz.id !in examHistory.quizIds) throw InvalidQuizException()

            if (isAnswer) {
                examHistoryCommandPort.updateExamHistory(
                    examHistory.copy(correctAnswerCount = examHistory.correctAnswerCount + 1)
                )
            }
            val quizHistory =
                QuizHistory.Creation(
                    examHistoryId = examHistory.id,
                    quizId = quizId,
                    quizPickId = quizPickId.takeIf { quizPickId > 0 },
                    isAnswer = isAnswer
                )
            quizHistoryCommandPort.createQuizHistory(quizHistory)

            return GradeQuizResult(
                contents = answerPick.contents,
                isAnswer = isAnswer,
                isTimeout = isTimeout
            )
        }
    }
}
