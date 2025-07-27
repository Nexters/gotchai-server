package com.gotchai.api.common

import com.gotchai.domain.auth.Provider
import com.gotchai.domain.fixture.createUser
import com.gotchai.domain.user.User
import com.gotchai.domain.user.UserService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext

@WebMvcTest
@AutoConfigureRestDocs
abstract class ControllerTest : DescribeSpec() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var restDocumentationContextProvider: RestDocumentationContextProvider

    @MockkBean
    private lateinit var userService: UserService

    protected val webClient by lazy {
        MockMvcWebTestClient
            .bindToApplicationContext(webApplicationContext)
            .configureClient()
            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentationContextProvider))
            .build()
    }

    override suspend fun beforeSpec(spec: Spec) {
        val user = createUser()

        every { userService.getUserIssue(any()) } returns User.Issue(id = user.id)
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(Provider(userId = user.id), null, setOf(SimpleGrantedAuthority("USER")))
    }
}
