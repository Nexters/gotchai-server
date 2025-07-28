package com.gotchai.domain.global.security

import com.gotchai.domain.user.entity.Role
import com.gotchai.domain.user.entity.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class GotchaiAuthentication(
    val userId: Long,
    val roles: Set<Role>,
) : Authentication {
    companion object {
        fun from(user: User): GotchaiAuthentication =
            with(user) {
                GotchaiAuthentication(
                    userId = id,
                    roles = roles
                )
            }

        fun from(payload: Map<String, *>): GotchaiAuthentication =
            with(payload) {
                GotchaiAuthentication(
                    userId = (get("userId") as String).toLong(),
                    roles = (get("roles") as String)
                        .split(",")
                        .map(Role::valueOf)
                        .toHashSet()
                )
            }

    }

    override fun getAuthorities(): Set<GrantedAuthority> = roles.map { SimpleGrantedAuthority(it.name) }.toSet()

    override fun getName(): String? = null

    override fun getCredentials(): Any? = null

    override fun getDetails(): Any? = null

    override fun getPrincipal(): Any? = null

    override fun isAuthenticated(): Boolean = true

    override fun setAuthenticated(isAuthenticated: Boolean) {
        throw UnsupportedOperationException()
    }

    fun toPayload(): Map<String, String> =
        hashMapOf(
            "userId" to userId.toString(),
            "roles" to roles.joinToString(",") { it.name }
        )
}
