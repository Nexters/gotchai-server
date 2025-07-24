package com.gotchai.storage.rdb.user.repository

import com.gotchai.domain.global.exception.ErrorException
import com.gotchai.domain.global.exception.ErrorType
import com.gotchai.domain.user.User
import com.gotchai.domain.user.UserRepository
import com.gotchai.storage.rdb.global.common.ReadOnlyTransactional
import com.gotchai.storage.rdb.global.util.findByIdOrElseThrow
import com.gotchai.storage.rdb.user.entity.UserEntity
import org.springframework.stereotype.Repository

@Repository
class UserCoreRepository(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun save(gotchaiCreation: User.GotchaiCreation) {
        userJpaRepository.save(
            UserEntity(
                gotchaiCreation,
            ),
        )
    }

    @ReadOnlyTransactional
    override fun findBy(userId: Long): User.Issue = userJpaRepository.findByIdOrElseThrow(userId).toIssue()

    @ReadOnlyTransactional
    override fun findCredentialByEmail(email: String): User.Credential {
        val user = userJpaRepository.findByEmail(email) ?: throw ErrorException(ErrorType.NOT_FOUND_USER)
        return user.toCredential()
    }

    @ReadOnlyTransactional
    override fun existsByEmail(email: String): Boolean {
        val exist = userJpaRepository.existsByEmail(email)
        return exist
    }
}
