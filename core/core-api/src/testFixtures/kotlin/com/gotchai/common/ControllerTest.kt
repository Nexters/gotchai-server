package com.gotchai.common

import io.kotest.core.spec.style.DescribeSpec
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient

abstract class ControllerTest : DescribeSpec() {
    private val restDocumentation = ManualRestDocumentation()

    protected fun <T> createWebClient(controller: T): WebTestClient =
        MockMvcWebTestClient.bindToController(controller)
            .configureClient()
            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
}
