package com.gotchai.storage.redis.exam.adapter.out

import com.gotchai.domain.exam.entity.ExamHistory
import com.gotchai.domain.exam.port.out.ExamHistoryCommandPort
import com.gotchai.domain.quiz.entity.QuizHistory
import com.gotchai.storage.redis.exam.entity.ExamHistoryEntity
import com.gotchai.storage.redis.exam.repository.ExamHistoryRedisRepository
import com.gotchai.storage.redis.global.annotation.Adapter

@Adapter
class ExamHistoryCommandAdapter(
    private val examHistoryRedisRepository: ExamHistoryRedisRepository
) : ExamHistoryCommandPort {
    override fun create(creation: ExamHistory.Creation) {
        examHistoryRedisRepository.save(
            ExamHistoryEntity.from(creation)
        )
    }

    override fun updateHistory(
        historyId: String,
        histories: List<QuizHistory>
    ) {
        examHistoryRedisRepository.findById(historyId).ifPresent {
            it.updateHistories(histories)
            examHistoryRedisRepository.save(it)
        }
    }
}
