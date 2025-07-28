package com.gotchai.storage.rdb.quiz.repository

import com.gotchai.storage.rdb.quiz.entity.QuizPickEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizPickJpaRepository : JpaRepository<QuizPickEntity, Long>
