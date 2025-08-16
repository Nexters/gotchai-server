package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.dto.result.GetMyExamResult
import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.exception.ExamHistoryNotFoundException
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
    override fun getExams(userId: Long): List<GetExamResult> =
        examQueryPort
            .getExamsWithExamHistoryByUserIdAndIsSolved(userId, null)
            .map { GetExamResult.of(it.exam, isSolved = it.examHistory?.isSolved ?: false) }

    @Transactional(readOnly = true)
    override fun getMyExams(userId: Long): List<GetMyExamResult> =
        examQueryPort
            .getExamsWithExamHistoryByUserIdAndIsSolved(userId, true)
            .map { GetMyExamResult.of(it.exam, it.examHistory ?: throw ExamHistoryNotFoundException()) }

    @Transactional(readOnly = true)
    override fun getExamParticipantCountById(examId: Long): Int =
        examHistoryQueryPort
            .getExamHistoriesByExamIdAndIsSolved(examId, true)
            .count()
}
