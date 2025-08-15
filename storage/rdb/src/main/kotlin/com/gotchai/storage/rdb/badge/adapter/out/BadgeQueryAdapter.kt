package com.gotchai.storage.rdb.badge.adapter.out

import com.gotchai.domain.badge.dto.projection.BadgeWithUserBadge
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
    override fun getBadgesWithUserBadgeByUserId(userId: Long): List<BadgeWithUserBadge> {
        val query =
            jpql {
                selectNew<Pair<BadgeEntity, UserBadgeEntity>>(
                    entity(BadgeEntity::class),
                    entity(UserBadgeEntity::class)
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
        val pairs =
            entityManager
                .createQuery(query, renderContext)
                .resultList

        return pairs.map { (badgeEntity, userBadgeEntity) ->
            BadgeWithUserBadge(
                badge = badgeEntity.toBadge(),
                userBadge = userBadgeEntity.toUserBadge()
            )
        }
    }
}
