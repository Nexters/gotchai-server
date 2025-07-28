package com.gotchai.storage.rdb.quiz.repository

import com.gotchai.storage.rdb.quiz.entity.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizJpaRepository : JpaRepository<QuizEntity, Long> {
    fun findAllByExamIdOrderByOrderAsc(examId: Long): List<QuizEntity>
}
