package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.exam.dto.result.ExamResult
import com.gotchai.domain.exam.exception.ExamNotFoundException
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.domain.exam.port.out.ExamQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExamQueryService(
    private val examQueryPort: ExamQueryPort,
    private val examHistoryQueryPort: ExamHistoryQueryPort
) : ExamQueryUseCase {
    @Transactional(readOnly = true)
    override fun getExamById(
        userId: Long,
        examId: Long
    ): ExamResult {
        val exam = examQueryPort.getExamById(examId) ?: throw ExamNotFoundException()
        val examHistory = examHistoryQueryPort.getExamHistoryByExamIdAndUserId(examId, userId)
        val isSolved = examHistory?.isSolved ?: false

        return ExamResult.of(
            exam = exam,
            isSolved = isSolved
        )
    }

    @Transactional(readOnly = true)
    override fun getExams(userId: Long): List<ExamResult> {
        val exams = examQueryPort.getExams()
        val examHistories = examHistoryQueryPort.getExamHistoriesByUserIdAndSolvedTrue(userId)
        val solvedExamIds = examHistories.map { it.id }

        return exams.map { exam ->
            val solved = solvedExamIds.contains(exam.id)
            ExamResult.of(
                exam = exam,
                isSolved = solved
            )
        }
    }

    @Transactional(readOnly = true)
    override fun getExamsByUserId(userId: Long): List<ExamResult> {
        val examHistories = examHistoryQueryPort.getExamHistoriesByUserIdAndSolvedTrue(userId)
        val exams = examQueryPort.getExamsByInIn(examHistories.map { it.id })

        val solvedExamIds = examHistories.map { it.id }

        return exams.map { exam ->
            val solved = solvedExamIds.contains(exam.id)
            ExamResult.of(
                exam = exam,
                isSolved = solved
            )
        }
    }

    @Transactional(readOnly = true)
    override fun getExamParticipantCountById(examId: Long): Int =
        examHistoryQueryPort
            .getExamHistoriesByExamIdAndSolvedTrue(examId)
            .count()
}
