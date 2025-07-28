package com.gotchai.storage.rdb.exam.repository

import com.gotchai.storage.rdb.exam.entity.ExamEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExamJpaRepository : JpaRepository<ExamEntity, Long>
