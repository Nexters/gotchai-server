package com.gotchai.storage.rdb.exam.repository

import com.gotchai.domain.exam.entity.ExamType
import com.gotchai.storage.rdb.exam.entity.ExamEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExamJpaRepository : JpaRepository<ExamEntity, Long> {
    fun findByIdIn(ids: Collection<Long>): List<ExamEntity>

    fun findByType(type: ExamType): ExamEntity?

    fun findAllByType(type: ExamType): List<ExamEntity>

    fun findAllByTypeNot(type: ExamType): List<ExamEntity>
}
