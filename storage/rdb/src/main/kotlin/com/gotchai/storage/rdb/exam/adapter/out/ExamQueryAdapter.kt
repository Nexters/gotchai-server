package com.gotchai.storage.rdb.exam.adapter.out

import com.gotchai.domain.exam.dto.projection.ExamWithExamHistory
import com.gotchai.domain.exam.dto.projection.SolvedExamWithExamHistory
import com.gotchai.domain.exam.entity.Exam
import com.gotchai.domain.exam.port.out.ExamQueryPort
import com.gotchai.storage.rdb.exam.entity.ExamEntity
import com.gotchai.storage.rdb.exam.entity.ExamHistoryEntity
import com.gotchai.storage.rdb.exam.repository.ExamJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional
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
    override fun getSolvedExamsWithExamHistoryByUserIdAndIsSolved(
        userId: Long,
        isSolved: Boolean?
    ): List<SolvedExamWithExamHistory> {
        val query =
            jpql {
                selectNew<Pair<ExamEntity, ExamHistoryEntity>>(
                    entity(ExamEntity::class),
                    entity(ExamHistoryEntity::class)
                ).from(
                    entity(ExamEntity::class),
                    innerJoin(ExamHistoryEntity::class)
                        .on(
                            path(ExamHistoryEntity::examId)
                                .eq(path(ExamEntity::id))
                                .and(path(ExamHistoryEntity::userId).eq(userId))
                                .apply { if (isSolved != null) and(path(ExamHistoryEntity::isSolved).eq(isSolved)) }
                        )
                )
            }
        val pairs = entityManager.createQuery(query, jdslRenderContext).resultList

        return pairs.map { (examEntity, examHistoryEntity) ->
            SolvedExamWithExamHistory(
                exam = examEntity.toExam(),
                examHistory = examHistoryEntity.toExamHistory()
            )
        }
    }

    @ReadOnlyTransactional
    override fun getExamsWithExamHistoryByUserIdAndIsSolved(
        userId: Long,
        isSolved: Boolean?
    ): List<ExamWithExamHistory> {
        val query =
            jpql {
                selectNew<Pair<ExamEntity, ExamHistoryEntity?>>(
                    entity(ExamEntity::class),
                    entity(ExamHistoryEntity::class)
                ).from(
                    entity(ExamEntity::class),
                    leftJoin(ExamHistoryEntity::class)
                        .on(
                            path(ExamHistoryEntity::examId)
                                .eq(path(ExamEntity::id))
                                .and(path(ExamHistoryEntity::userId).eq(userId))
                                .apply { if (isSolved != null) and(path(ExamHistoryEntity::isSolved).eq(isSolved)) }
                        )
                )
            }
        val pairs = entityManager.createQuery(query, jdslRenderContext).resultList

        return pairs.map { (examEntity, examHistoryEntity) ->
            ExamWithExamHistory(
                exam = examEntity.toExam(),
                examHistory = examHistoryEntity?.toExamHistory()
            )
        }
    }

    @ReadOnlyTransactional
    override fun getExamCount(): Long = examJpaRepository.count()
}
