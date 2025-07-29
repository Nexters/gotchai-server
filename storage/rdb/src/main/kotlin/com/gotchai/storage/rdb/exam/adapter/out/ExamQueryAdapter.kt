package com.gotchai.storage.rdb.exam.adapter.out

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.storage.rdb.exam.repository.ExamJpaRepository
import com.gotchai.storage.rdb.global.common.ReadOnlyTransactional
import com.gotchai.storage.rdb.global.util.findByIdOrElseThrow
import org.springframework.stereotype.Repository

@Repository
class ExamQueryAdapter(
    private val examJpaRepository: ExamJpaRepository,
) : ExamQueryPort {
    @ReadOnlyTransactional
    override fun getExamById(examId: Long): Exam = examJpaRepository.findByIdOrElseThrow(examId).toExam()

    @ReadOnlyTransactional
    override fun getExams(): List<Exam> = examJpaRepository.findAll().map { it.toExam() }
}
