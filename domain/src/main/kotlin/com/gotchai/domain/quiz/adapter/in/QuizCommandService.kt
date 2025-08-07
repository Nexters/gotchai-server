package com.gotchai.domain.quiz.adapter.`in`

import com.gotchai.domain.exam.exception.ExamAlreadySolvedException
import com.gotchai.domain.exam.exception.ExamHistoryNotFoundException
import com.gotchai.domain.exam.port.out.ExamHistoryCommandPort
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.quiz.dto.command.GradeQuizCommand
import com.gotchai.domain.quiz.entity.QuizHistory
import com.gotchai.domain.quiz.entity.QuizPick
import com.gotchai.domain.quiz.exception.InvalidQuizPickException
import com.gotchai.domain.quiz.exception.QuizPickNotFoundException
import com.gotchai.domain.quiz.port.`in`.QuizCommandUseCase
import com.gotchai.domain.quiz.port.out.QuizHistoryCommandPort
import com.gotchai.domain.quiz.port.out.QuizPickQueryPort
import org.springframework.stereotype.Service

@Service
class QuizCommandService(
    private val quizPickQueryPort: QuizPickQueryPort,
    private val quizHistoryCommandPort: QuizHistoryCommandPort,
    private val examHistoryQueryPort: ExamHistoryQueryPort,
    private val examHistoryCommandPort: ExamHistoryCommandPort
) : QuizCommandUseCase {
    override fun gradeQuiz(
        userId: Long,
        command: GradeQuizCommand
    ): QuizPick =
        with(command) {
            val examHistory =
                examHistoryQueryPort.getExamHistoryByExamIdAndUserId(examId, userId)
                    ?: throw ExamHistoryNotFoundException()
            val quizPick = quizPickQueryPort.getQuizPickById(quizPickId) ?: throw QuizPickNotFoundException()

            if (examHistory.isSolved) throw ExamAlreadySolvedException()
            if (quizPick.quizId !in examHistory.quizIds) throw InvalidQuizPickException()

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
