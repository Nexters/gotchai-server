package com.gotchai.storage.rdb.exam.adapter.out

import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.port.out.ExamCommandPort
import com.gotchai.storage.rdb.exam.entity.ExamEntity
import com.gotchai.storage.rdb.exam.repository.ExamJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter

@Adapter
class ExamCommandAdapter(
    private val examJpaRepository: ExamJpaRepository
) : ExamCommandPort {
    override fun createExam(creation: Exam.Creation): Exam =
        examJpaRepository.save(ExamEntity.from(creation))
            .toExam()
}
