package com.gotchai.storage.rdb.badge.entity

import com.gotchai.domain.badge.entity.Badge
import com.gotchai.domain.badge.entity.Rank
import com.gotchai.storage.rdb.global.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import jakarta.persistence.Column

@Entity
@Table(name = "badge")
class BadgeEntity(
    val examId: Long,
    val name: String,
    val description: String,
    val image: String,
    @Enumerated(value = EnumType.STRING)
    @Column(name = "rank", columnDefinition = "varchar(20)")
    val rank: Rank,
) : BaseEntity() {
    companion object {
        fun from(creation: Badge.Creation): BadgeEntity =
            with(creation) {
                BadgeEntity(
                    examId = examId,
                    name = name,
                    description = description,
                    image = image,
                    rank = rank
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
            rank = rank,
            createdAt = createdAt,
        )
}
