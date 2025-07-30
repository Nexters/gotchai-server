package com.gotchai.storage.rdb.global.util

import com.gotchai.domain.global.exception.NotFoundDataException
import com.gotchai.storage.rdb.global.entity.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun <T : BaseEntity> JpaRepository<T, Long>.findByIdOrElseThrow(id: Long): T {
    val value = findByIdOrNull(id) ?: throw NotFoundDataException()
    return value
}

fun <T : BaseEntity> JpaRepository<T, Long>.findByIdAndDeletedAtIsNullOrElseThrow(id: Long): T {
    val value = findByIdOrNull(id).takeIf { it?.deletedAt == null } ?: throw NotFoundDataException()
    return value
}
