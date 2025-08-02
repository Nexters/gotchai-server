package com.gotchai.storage.rdb.exam.repository

import com.gotchai.storage.rdb.exam.entity.ExamResultEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExamResultJpaRepository : JpaRepository<ExamResultEntity, Long> {
    fun findExamResultsByUserId(userId: Long): List<ExamResultEntity>

    fun countByExamId(examId: Long): Int
}
