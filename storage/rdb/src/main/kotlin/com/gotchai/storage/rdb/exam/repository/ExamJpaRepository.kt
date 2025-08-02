package com.gotchai.storage.rdb.exam.repository

import com.gotchai.storage.rdb.exam.entity.ExamEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExamJpaRepository : JpaRepository<ExamEntity, Long> {
    fun findByIdIn(ids: Collection<Long>): List<ExamEntity>
}
