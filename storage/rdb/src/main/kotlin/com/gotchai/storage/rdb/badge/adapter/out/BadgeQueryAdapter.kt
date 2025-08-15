package com.gotchai.storage.rdb.badge.adapter.out

import com.gotchai.domain.badge.dto.projection.BadgeWithAcquiredAt
import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier
import com.gotchai.domain.badge.port.out.BadgeQueryPort
import com.gotchai.storage.rdb.badge.entity.BadgeEntity
import com.gotchai.storage.rdb.badge.entity.UserBadgeEntity
import com.gotchai.storage.rdb.badge.repository.BadgeJpaRepository
import com.gotchai.storage.rdb.global.annotation.Adapter
import com.gotchai.storage.rdb.global.annotation.ReadOnlyTransactional
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.extension.createQuery
import jakarta.persistence.EntityManager
import org.springframework.data.repository.findByIdOrNull

@Adapter
class BadgeQueryAdapter(
    private val badgeRepository: BadgeJpaRepository,
    private val entityManager: EntityManager,
    private val renderContext: RenderContext
) : BadgeQueryPort {
    @ReadOnlyTransactional
    override fun getBadgeById(id: Long): Badge? =
        badgeRepository
            .findByIdOrNull(id)
            ?.toBadge()

    @ReadOnlyTransactional
    override fun getBadgeByExamIdAndTier(
        examId: Long,
        badgeTier: Tier
    ): Badge? =
        badgeRepository
            .findByExamIdAndTier(examId, badgeTier)
            ?.toBadge()

    @ReadOnlyTransactional
    override fun getBadgesWithAcquiredAtByUserId(userId: Long): List<BadgeWithAcquiredAt> {
        val query =
            jpql {
                selectNew<BadgeWithAcquiredAt>(
                    path(BadgeEntity::id),
                    path(BadgeEntity::examId),
                    path(BadgeEntity::name),
                    path(BadgeEntity::description),
                    path(BadgeEntity::image),
                    path(BadgeEntity::tier),
                    path(BadgeEntity::createdAt),
                    path(UserBadgeEntity::createdAt)
                ).from(
                    entity(BadgeEntity::class),
                    innerJoin(UserBadgeEntity::class)
                        .on(path(UserBadgeEntity::badgeId).eq(path(BadgeEntity::id)))
                ).where(
                    path(UserBadgeEntity::userId).eq(userId)
                ).orderBy(
                    path(UserBadgeEntity::createdAt).desc()
                )
            }

        return entityManager
            .createQuery(query, renderContext)
            .resultList
    }
}
