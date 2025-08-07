package com.gotchai.storage.rdb.quiz.repository

import com.gotchai.storage.rdb.quiz.entity.QuizHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizHistoryJpaRepository : JpaRepository<QuizHistoryEntity, Long> {
    fun findAllByExamHistoryId(examHistoryId: Long): List<QuizHistoryEntity>

    fun deleteAllByExamHistoryId(examHistoryId: Long)
}
