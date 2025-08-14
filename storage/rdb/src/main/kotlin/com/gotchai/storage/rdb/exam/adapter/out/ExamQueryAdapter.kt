package com.gotchai.storage.rdb.exam.adapter.out

import com.gotchai.domain.exam.dto.projection.ExamWithIsSolved
import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.storage.rdb.exam.entity.ExamEntity
import com.gotchai.storage.rdb.exam.entity.ExamHistoryEntity
import com.gotchai.storage.rdb.exam.repository.ExamJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional
import com.gotchai.storage.rdb.global.util.JdslUtil
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import org.springframework.data.repository.findByIdOrNull

@Adapter
class ExamQueryAdapter(
    private val entityManager: EntityManager,
    private val jdslRenderContext: RenderContext,
    private val examJpaRepository: ExamJpaRepository
) : ExamQueryPort {
    @ReadOnlyTransactional
    override fun getExamById(id: Long): Exam? = examJpaRepository.findByIdOrNull(id)?.toExam()

    @ReadOnlyTransactional
    override fun getExams(): List<Exam> = examJpaRepository.findAll().map { it.toExam() }

    @ReadOnlyTransactional
    override fun getExamResultsByUserId(userId: Long): List<ExamWithIsSolved> =
        createExamResultQuery(
            userId = userId
        )

    @ReadOnlyTransactional
    override fun getExamResultsByUserIdAndExamId(
        userId: Long,
        examId: Long
    ): ExamWithIsSolved? =
        createExamResultQuery(
            userId = userId,
            examId = examId
        ).firstOrNull()

    @ReadOnlyTransactional
    override fun getExamsByInIn(ids: Collection<Long>): List<Exam> = examJpaRepository.findByIdIn(ids).map { it.toExam() }

    @ReadOnlyTransactional
    override fun getExamResultsByUserIdWithSolvedStatus(
        userId: Long,
        isSolved: Boolean
    ): List<ExamWithIsSolved> =
        createExamResultQuery(
            userId = userId,
            isSolved = isSolved
        )

    @ReadOnlyTransactional
    override fun getExamCount(): Long = examJpaRepository.count()

    private fun createExamResultQuery(
        userId: Long,
        examId: Long? = null,
        isSolved: Boolean? = null
    ): List<ExamWithIsSolved> {
        val query =
            jpql(JdslUtil) {
                selectNew<ExamWithIsSolved>(
                    path(ExamEntity::id),
                    path(ExamEntity::title),
                    path(ExamEntity::subTitle),
                    path(ExamEntity::description),
                    path(ExamEntity::prompt),
                    path(ExamEntity::backgroundImage),
                    path(ExamEntity::iconImage),
                    path(ExamEntity::coverImage),
                    path(ExamEntity::theme),
                    coalesce(path(ExamHistoryEntity::isSolved), false),
                    path(ExamEntity::createdAt)
                ).from(
                    entity(ExamEntity::class),
                    leftJoin(ExamHistoryEntity::class).on(
                        path(ExamHistoryEntity::examId)
                            .eq(path(ExamEntity::id))
                            .and(path(ExamHistoryEntity::userId).eq(userId))
                            .apply { if (isSolved != null) and(path(ExamHistoryEntity::isSolved).eq(isSolved)) }
                    )
                ).apply { if (examId != null) where(path(ExamEntity::id).eq(examId)) }
            }
        return entityManager.createQuery(query, jdslRenderContext).resultList
    }
}
