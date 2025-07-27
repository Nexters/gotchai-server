package com.gotchai.storage.rdb.quiz.repository

import com.gotchai.storage.rdb.quiz.entity.QuizContentsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizContentsJpaRepository : JpaRepository<QuizContentsEntity, Long>
