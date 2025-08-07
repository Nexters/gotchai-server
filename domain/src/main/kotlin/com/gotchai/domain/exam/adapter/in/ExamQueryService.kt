package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.exam.dto.result.GetExamResult
import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.entity.ExamType
import com.gotchai.domain.exam.exception.ExamNotFoundException
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.domain.exam.port.out.ExamResultQueryPort
import com.gotchai.domain.quiz.port.out.QuizQueryPort
import org.springframework.stereotype.Service

@Service
class ExamQueryService(
    private val examQueryPort: ExamQueryPort,
    private val examResultQueryPort: ExamResultQueryPort,
    private val quizQueryPort: QuizQueryPort
) : ExamQueryUseCase {
    override fun getExamById(id: Long): Exam = examQueryPort.getExamById(id) ?: throw ExamNotFoundException()

    override fun getExamDetailById(id: Long): GetExamResult {
        val exam = examQueryPort.getExamById(id) ?: throw ExamNotFoundException()
        val quizzes = quizQueryPort.getQuizzesByExamId(id)

        return GetExamResult.of(exam, quizzes.map { it.id })
    }

    override fun getExamsByUserId(userId: Long): List<Exam> {
        val examResults = examResultQueryPort.getExamResultsByUserId(userId)
        val exams = examQueryPort.getExamsByInIn(examResults.map { it.id })

        return exams
    }

    override fun getExams(): List<Exam> = examQueryPort.getExamsByTypeNot(ExamType.ONBOARDING)

    override fun getExamParticipantCountById(id: Long): Int = examResultQueryPort.countExamResultsByExamId(id)
}
