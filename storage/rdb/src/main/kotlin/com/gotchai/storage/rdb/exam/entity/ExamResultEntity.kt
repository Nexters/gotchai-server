package com.gotchai.storage.rdb.exam.entity

import com.gotchai.domain.exam.entity.ExamResult
import com.gotchai.storage.rdb.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "exam_result")
@Entity
class ExamResultEntity(
    val examId: Long,
    val userId: Long,
    val takeQuizIds: String,
    val answerQuizIds: String?,
    val failedQuizIds: String?
) : BaseEntity() {
    companion object {
        fun from(creation: ExamResult.Creation): ExamResultEntity =
            with(creation) {
                ExamResultEntity(
                    examId = examId,
                    userId = userId,
                    takeQuizIds = takeQuizIds,
                    answerQuizIds = answerQuizIds,
                    failedQuizIds = failedQuizIds
                )
            }
    }

    fun toExamResult(): ExamResult =
        ExamResult(
            id = id!!,
            examId = examId,
            userId = userId,
            takeQuizIds = takeQuizIds,
            answerQuizIds = answerQuizIds,
            failedQuizIds = failedQuizIds,
            createdAt = createdAt
        )
}
