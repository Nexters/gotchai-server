package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.exam.port.out.ExamQueryPort
import org.springframework.stereotype.Service

@Service
class ExamQueryService(
    private val examQueryPort: ExamQueryPort,
) : ExamQueryUseCase {
    override fun getExamById(examId: Long): Exam = examQueryPort.getExamById(examId)

    override fun getExams(): List<Exam> = examQueryPort.getExams()
}
