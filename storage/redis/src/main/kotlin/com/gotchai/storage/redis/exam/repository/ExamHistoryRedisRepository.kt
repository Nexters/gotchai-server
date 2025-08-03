package com.gotchai.storage.redis.exam.repository

import com.gotchai.storage.redis.exam.entity.ExamHistoryEntity
import org.springframework.data.repository.CrudRepository

interface ExamHistoryRedisRepository : CrudRepository<ExamHistoryEntity, String> {
    fun findByHistoryId(historyId: String): ExamHistoryEntity?
}
