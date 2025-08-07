package com.gotchai.storage.rdb.exam.adapter.out

import com.gotchai.domain.exam.entity.ExamResult
import com.gotchai.domain.exam.port.out.ExamResultCommandPort
import com.gotchai.storage.rdb.exam.entity.ExamResultEntity
import com.gotchai.storage.rdb.exam.repository.ExamResultJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter

@Adapter
class ExamResultCommandAdapter(
    private val examResultJpaRepository: ExamResultJpaRepository
) : ExamResultCommandPort {
    override fun createExamResult(creation: ExamResult.Creation) {
        examResultJpaRepository.save(
            ExamResultEntity.from(creation)
        )
    }
}
