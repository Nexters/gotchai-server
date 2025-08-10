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
    ): ExamResult =
        examQueryPort.getExamResultsByUserIdAndExamId(userId, examId)
            ?: throw ExamNotFoundException()

    @Transactional(readOnly = true)
    override fun getExams(userId: Long): List<ExamResult> = examQueryPort.getExamResultsByUserId(userId)

    @Transactional(readOnly = true)
    override fun getExamsByUserId(userId: Long): List<ExamResult> = examQueryPort.getExamResultsByUserIdWithSolvedStatus(userId, true)

    @Transactional(readOnly = true)
    override fun getExamParticipantCountById(examId: Long): Int =
        examHistoryQueryPort
            .getExamHistoriesByExamIdAndSolvedTrue(examId)
            .count()
}
