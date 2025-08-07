package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.exception.ExamNotFoundException
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.domain.quiz.port.out.QuizQueryPort
import org.springframework.stereotype.Service

@Service
class ExamQueryService(
    private val examQueryPort: ExamQueryPort,
    private val examHistoryQueryPort: ExamHistoryQueryPort,
    private val quizQueryPort: QuizQueryPort
) : ExamQueryUseCase {
    override fun getExamById(id: Long): GetExamResult {
        val exam = examQueryPort.getExamById(id) ?: throw ExamNotFoundException()
        val quizzes = quizQueryPort.getQuizzesByExamId(id)

        return GetExamResult.of(exam, quizzes.map { it.id })
    }

    override fun getExams(): List<Exam> = examQueryPort.getExams()

    override fun getExamsByUserId(userId: Long): List<Exam> {
        val examHistories = examHistoryQueryPort.getExamHistoriesByUserIdAndSolvedTrue(userId)
        val exams = examQueryPort.getExamsByInIn(examHistories.map { it.id })

        return exams
    }

    override fun getExamParticipantCountById(id: Long): Int =
        examHistoryQueryPort
            .getExamHistoriesByExamIdAndSolvedTrue(id)
            .count()
}
