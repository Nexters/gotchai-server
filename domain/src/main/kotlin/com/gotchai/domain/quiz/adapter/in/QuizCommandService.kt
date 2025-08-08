package com.gotchai.domain.quiz.adapter.`in`

import com.gotchai.domain.exam.exception.*
import com.gotchai.domain.exam.port.out.*
import com.gotchai.domain.quiz.dto.command.GradeQuizCommand
import com.gotchai.domain.quiz.entity.QuizHistory
import com.gotchai.domain.quiz.entity.QuizPick
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
    ): QuizPick =
        with(command) {
            val quiz = quizQueryPort.getQuizById(quizId) ?: throw QuizNotFoundException()
            val examHistory =
                examHistoryQueryPort.getExamHistoryByExamIdAndUserId(quiz.examId, userId)
                    ?: throw ExamHistoryNotFoundException()

            if (isTimeout) {
                quizHistoryCommandPort.createQuizHistory(
                    QuizHistory.Creation(
                        examHistoryId = examHistory.id,
                        quizId = quizId,
                        quizPickId = null,
                        isAnswer = false
                    )
                )
            }

            val quizPick = quizPickQueryPort.getQuizPickById(quizPickId) ?: throw QuizPickNotFoundException()

            if (examHistory.isSolved) throw ExamAlreadySolvedException()
            if (quiz.id !in examHistory.quizIds) throw InvalidQuizPickException()

            if (quizPick.isAnswer) {
                examHistoryCommandPort.updateExamHistory(
                    examHistory.copy(correctAnswerCount = examHistory.correctAnswerCount + 1)
                )
            }

            quizHistoryCommandPort.createQuizHistory(
                QuizHistory.Creation(
                    examHistoryId = examHistory.id,
                    quizId = quizId,
                    quizPickId = quizPick.id,
                    isAnswer = quizPick.isAnswer
                )
            )

            quizPick
        }
}
