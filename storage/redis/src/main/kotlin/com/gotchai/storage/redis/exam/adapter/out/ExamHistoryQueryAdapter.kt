package com.gotchai.storage.redis.exam.adapter.out

import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.domain.exam.port.out.ExamHistoryQueryPort
import com.gotchai.storage.redis.exam.repository.ExamHistoryRedisRepository
import com.gotchai.storage.redis.global.annotation.Adapter

@Adapter
class ExamHistoryQueryAdapter(
    private val examHistoryRedisRepository: ExamHistoryRedisRepository
) : ExamHistoryQueryPort {
    override fun getHistoryById(historyId: String): ExamHistory? =
        examHistoryRedisRepository.findByHistoryId(historyId)?.toExamHistory()
}
