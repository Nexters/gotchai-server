package com.gotchai.storage.rdb.user

import com.gotchai.domain.user.User
import com.gotchai.domain.user.UserRepository
import com.gotchai.storage.rdb.global.common.ReadOnlyTransactional
import com.gotchai.storage.rdb.global.util.findByIdOrElseThrow
import org.springframework.stereotype.Repository

@Repository
class UserCoreRepository(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    @ReadOnlyTransactional
    override fun readBy(userId: Long): User.Issue = userJpaRepository.findByIdOrElseThrow(userId).toIssue()
}
