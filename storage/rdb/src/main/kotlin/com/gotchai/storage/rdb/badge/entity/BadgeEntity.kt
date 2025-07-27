package com.gotchai.storage.rdb.badge.entity

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Tier
import com.gotchai.storage.rdb.global.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "badge")
class BadgeEntity(
    val examId: Long,
    val name: String,
    val description: String,
    val image: String,
    @Enumerated(value = EnumType.STRING)
    @Column(name = "tier", columnDefinition = "varchar(20)")
    val tier: Tier,
) : BaseEntity() {
    companion object {
        fun from(creation: Badge.Creation): BadgeEntity =
            with(creation) {
                BadgeEntity(
                    examId = examId,
                    name = name,
                    description = description,
                    image = image,
                    tier = tier,
                )
            }
    }

    fun toBadge(): Badge =
        Badge(
            id = id!!,
            examId = examId,
            name = name,
            description = description,
            image = image,
            tier = tier,
            createdAt = createdAt,
        )
}
