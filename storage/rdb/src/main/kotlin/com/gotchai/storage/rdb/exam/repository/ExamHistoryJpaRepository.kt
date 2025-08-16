package com.gotchai.storage.rdb.exam.repository

import com.gotchai.storage.rdb.exam.entity.ExamHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ExamHistoryJpaRepository : JpaRepository<ExamHistoryEntity, Long> {
    fun findByExamIdAndUserId(
        examId: Long,
        userId: Long
    ): ExamHistoryEntity?

    @Query("SELECT e FROM ExamHistoryEntity e WHERE e.examId = :examId AND e.isSolved = :isSolved")
    fun findAllByExamIdAndIsSolved(
        @Param("examId") examId: Long,
        @Param("isSolved") isSolved: Boolean
    ): List<ExamHistoryEntity>

    @Query("SELECT e FROM ExamHistoryEntity e WHERE e.userId = :userId AND e.isSolved = :isSolved")
    fun findAllByUserIdAndIsSolved(
        @Param("userId") userId: Long,
        @Param("isSolved") isSolved: Boolean
    ): List<ExamHistoryEntity>

    @Query("SELECT e FROM ExamHistoryEntity e JOIN FETCH e.quizIds")
    fun findAllWithQuizIds(): List<ExamHistoryEntity>
}
