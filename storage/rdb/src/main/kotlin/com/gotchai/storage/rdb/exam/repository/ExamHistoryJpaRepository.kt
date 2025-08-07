package com.gotchai.storage.rdb.exam.repository

import com.gotchai.storage.rdb.exam.entity.ExamHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExamHistoryJpaRepository : JpaRepository<ExamHistoryEntity, Long> {
    fun findByExamIdAndUserId(
        examId: Long,
        userId: Long
    ): ExamHistoryEntity?

    fun findAllByExamIdAndIsSolvedTrue(examId: Long): List<ExamHistoryEntity>

    fun findAllByUserIdAndIsSolvedTrue(userId: Long): List<ExamHistoryEntity>
}
