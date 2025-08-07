package com.gotchai.storage.rdb.exam.adapter.out

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.storage.rdb.exam.repository.ExamJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional
import org.springframework.data.repository.findByIdOrNull

@Adapter
class ExamQueryAdapter(
    private val examJpaRepository: ExamJpaRepository
) : ExamQueryPort {
    @ReadOnlyTransactional
    override fun getExamById(id: Long): Exam? =
        examJpaRepository
            .findByIdOrNull(id)
            ?.toExam()

    @ReadOnlyTransactional
    override fun getExams(): List<Exam> =
        examJpaRepository
            .findAll()
            .map { it.toExam() }

    @ReadOnlyTransactional
    override fun getExamsByInIn(ids: Collection<Long>): List<Exam> =
        examJpaRepository
            .findByIdIn(ids)
            .map { it.toExam() }
}
