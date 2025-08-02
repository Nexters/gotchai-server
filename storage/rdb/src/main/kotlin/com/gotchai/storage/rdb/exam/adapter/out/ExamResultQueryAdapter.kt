package com.gotchai.storage.rdb.exam.adapter.out

import com.gotchai.domain.exam.entity.ExamResult
import com.gotchai.domain.exam.port.out.ExamResultQueryPort
import com.gotchai.storage.rdb.exam.repository.ExamResultJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter

@Adapter
class ExamResultQueryAdapter(
    private val examResultJpaRepository: ExamResultJpaRepository
) : ExamResultQueryPort {
    override fun getExamResultsByUserId(userId: Long): List<ExamResult> =
        examResultJpaRepository.findExamResultsByUserId(userId)
            .map { it.toExamResult() }
}
