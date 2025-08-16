package com.gotchai.api.common

import com.gotchai.domain.fixture.createUser
import com.gotchai.domain.global.security.GotchaiAuthentication
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.web.context.WebApplicationContext

@AutoConfigureRestDocs
abstract class ControllerTest : DescribeSpec() {
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var restDocumentationContextProvider: RestDocumentationContextProvider

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override suspend fun beforeSpec(spec: Spec) {
        SecurityContextHolder.getContext().authentication = GotchaiAuthentication.from(createUser())
    }

    protected val webClient by lazy {
        MockMvcWebTestClient
            .bindToApplicationContext(webApplicationContext)
            .apply(springSecurity())
            .defaultRequest(post("/**").with(csrf()))
            .configureClient()
            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentationContextProvider))
            .build()
    }
}
