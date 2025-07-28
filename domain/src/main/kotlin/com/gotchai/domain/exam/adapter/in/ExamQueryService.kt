package com.gotchai.domain.exam.adapter.`in`

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.port.`in`.ExamQueryUseCase
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.domain.quiz.port.out.QuizQueryPort
import org.springframework.stereotype.Service

@Service
class ExamQueryService(
    private val examQueryPort: ExamQueryPort,
    private val quizQueryPort: QuizQueryPort,
) : ExamQueryUseCase {
    override fun getExamById(examId: Long): Exam {
        val exam = examQueryPort.getExamById(examId)

        val quizzes = quizQueryPort.getQuizzesByExamId(examId)
        TODO()
    }

    override fun getExams(): List<Exam> = examQueryPort.getExams()
}
