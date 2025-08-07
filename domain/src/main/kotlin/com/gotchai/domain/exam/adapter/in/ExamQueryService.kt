package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.exception.ExamNotFoundException
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.domain.quiz.port.out.QuizQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExamQueryService(
    private val examQueryPort: ExamQueryPort,
    private val examHistoryQueryPort: ExamHistoryQueryPort,
    private val quizQueryPort: QuizQueryPort
) : ExamQueryUseCase {
    @Transactional(readOnly = true)
    override fun getExamById(examId: Long): Exam = examQueryPort.getExamById(examId) ?: throw ExamNotFoundException()

    @Transactional(readOnly = true)
    override fun getExams(): List<Exam> = examQueryPort.getExams()

    @Transactional(readOnly = true)
    override fun getExamsByUserId(userId: Long): List<Exam> {
        val examHistories = examHistoryQueryPort.getExamHistoriesByUserIdAndSolvedTrue(userId)
        val exams = examQueryPort.getExamsByInIn(examHistories.map { it.id })

        return exams
    }

    @Transactional(readOnly = true)
    override fun getExamParticipantCountById(examId: Long): Int =
        examHistoryQueryPort
            .getExamHistoriesByExamIdAndSolvedTrue(examId)
            .count()
}
