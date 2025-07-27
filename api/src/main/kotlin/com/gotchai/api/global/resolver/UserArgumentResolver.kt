package com.gotchai.api.global.resolver

import com.gotchai.domain.auth.dto.Provider
import com.gotchai.domain.user.entity.User
import com.gotchai.domain.user.port.`in`.UserQueryUseCase
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class UserArgumentResolver(
    private val userQueryUseCase: UserQueryUseCase,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.parameterType == User.Issue::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        nativeWebRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): User.Issue? {
        val authentication = SecurityContextHolder.getContext().authentication.principal

        if (authentication != "anonymousUser") {
            val provider = authentication as Provider
            return userQueryUseCase.getUserIssue(provider.userId)
        }
        return null
    }
}
