package com.gotchai.storage.redis.quiz.repository

import com.gotchai.storage.redis.quiz.entity.QuizScoreEntity
import org.springframework.data.repository.CrudRepository

interface QuizScoreRedisRepository: CrudRepository<QuizScoreEntity, Long>
