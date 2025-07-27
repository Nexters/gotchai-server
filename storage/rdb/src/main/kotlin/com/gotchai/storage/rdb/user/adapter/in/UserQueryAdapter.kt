package com.gotchai.storage.rdb.user.adapter.`in`

import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.exception.UserNotFoundException
import com.gotchai.domain.user.port.out.UserQueryPort
import com.gotchai.storage.rdb.global.common.ReadOnlyTransactional
import com.gotchai.storage.rdb.global.util.findByIdOrElseThrow
import com.gotchai.storage.rdb.user.repository.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserQueryAdapter(
    private val userJpaRepository: UserJpaRepository,
) : UserQueryPort {
    @ReadOnlyTransactional
    override fun findAll(): List<User.Profile> = userJpaRepository.findAllByDeletedAtIsNull().map { it.toProfile() }

    @ReadOnlyTransactional
    override fun findBy(userId: Long): User.Issue = userJpaRepository.findByIdOrElseThrow(userId).toIssue()

    @ReadOnlyTransactional
    override fun findBySocialId(socialId: String): User.Info? = userJpaRepository.findBySocialIdAndDeletedAtIsNull(socialId)?.toUserInfo()

    @ReadOnlyTransactional
    override fun findCredentialByEmail(email: String): User.Credential {
        val user = userJpaRepository.findByEmail(email) ?: throw UserNotFoundException()
        return user.toCredential()
    }

    @ReadOnlyTransactional
    override fun existsByEmail(email: String): Boolean {
        val exist = userJpaRepository.existsByEmail(email)
        return exist
    }
}
