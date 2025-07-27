package com.gotchai.domain.user.adapter.`in`

import com.gotchai.domain.user.dto.SocialUser
import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.port.`in`.UserQueryUseCase
import com.gotchai.domain.user.port.out.UserQueryPort
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userQueryPort: UserQueryPort,
) : UserQueryUseCase {
    override fun getAll(): List<User.Profile> = userQueryPort.findAll()

    override fun getUserIssue(userId: Long): User.Issue = userQueryPort.findBy(userId)

    override fun getUserCredentialByEmail(email: String): User.Credential? = userQueryPort.findCredentialByEmail(email)

    override fun getUserCredentialBySocial(socialId: String): SocialUser? {
        val userInfo = userQueryPort.findBySocialId(socialId)
        return userInfo?.let {
            SocialUser(
                id = userInfo.id,
                name = userInfo.name,
                socialId = userInfo.socialId!!,
                socialType = userInfo.socialType,
            )
        }
    }
}
