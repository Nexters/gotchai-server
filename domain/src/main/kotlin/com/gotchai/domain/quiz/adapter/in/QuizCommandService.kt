package com.gotchai.domain.quiz.adapter.`in`

import com.gotchai.domain.exam.exception.ExamAlreadySolvedException
import com.gotchai.domain.exam.exception.ExamHistoryNotFoundException
import com.gotchai.domain.exam.port.out.ExamHistoryCommandPort
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.quiz.dto.command.GradeQuizCommand
import com.gotchai.domain.quiz.entity.QuizHistory
import com.gotchai.domain.quiz.entity.QuizPick
import com.gotchai.domain.quiz.exception.InvalidQuizPickException
import com.gotchai.domain.quiz.exception.QuizNotFoundException
import com.gotchai.domain.quiz.exception.QuizPickNotFoundException
import com.gotchai.domain.quiz.port.`in`.QuizCommandUseCase
import com.gotchai.domain.quiz.port.out.QuizHistoryCommandPort
import com.gotchai.domain.quiz.port.out.QuizPickQueryPort
import com.gotchai.domain.quiz.port.out.QuizQueryPort
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
            val quizPick = quizPickQueryPort.getQuizPickById(quizPickId) ?: throw QuizPickNotFoundException()

            if (examHistory.isSolved) throw ExamAlreadySolvedException()
            if (quiz.id !in examHistory.quizIds) throw InvalidQuizPickException()

            if (quizPick.isAnswer) {
                examHistoryCommandPort.updateExamHistory(
                    examHistory.run {
                        copy(
                            correctAnswerCount =
                                correctAnswerCount + 1
                        )
                    }
                )
            }

            quizPick.apply {
                quizHistoryCommandPort.createQuizHistory(
                    QuizHistory.Creation(
                        examHistoryId = examHistory.id,
                        quizId = quizId,
                        quizPickId = id,
                        isAnswer = isAnswer
                    )
                )
            }
        }
}
