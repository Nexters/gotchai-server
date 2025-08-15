package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.exam.dto.result.GetExamsResult
import com.gotchai.domain.exam.dto.result.GetMyExamsResult
import com.gotchai.domain.exam.entity.Exam
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
    override fun getExamById(examId: Long): Exam =
        examQueryPort.getExamById(examId)
            ?: throw ExamNotFoundException()

    @Transactional(readOnly = true)
    override fun getExams(userId: Long): List<GetExamsResult> =
        examQueryPort
            .getExamsWithExamHistoryByUserIdAndIsSolved(userId, null)
            .map { GetExamsResult.of(it.exam, it.examHistory) }

    @Transactional(readOnly = true)
    override fun getMyExams(userId: Long): List<GetMyExamsResult> =
        examQueryPort
            .getExamsWithExamHistoryByUserIdAndIsSolved(userId, true)
            .map { GetMyExamsResult.of(it.exam, it.examHistory) }

    @Transactional(readOnly = true)
    override fun getExamParticipantCountById(examId: Long): Int =
        examHistoryQueryPort
            .getExamHistoriesByExamIdAndIsSolved(examId, true)
            .count()
}
