package com.gotchai.util

import com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec

infix fun String.bodyDesc(description: String): FieldDescriptor =
    fieldWithPath(this)
        .description(description)

infix fun String.paramDesc(description: String): ParameterDescriptor =
    parameterWithName(this)
        .description(description)

fun List<FieldDescriptor>.toListFields(): List<FieldDescriptor> =
    map { "[].${it.path}" bodyDesc it.description as String }

fun <T> BodySpec<T, *>.document(
    identifier: String,
    init: DocumentDsl<T>.() -> Unit,
): BodySpec<T, *> =
    DocumentDsl(identifier, this)
        .apply(init)
        .build()

class DocumentDsl<T>(
    private val identifier: String,
    private val contentSpec: BodySpec<T, *>,
) {
    private val snippets: MutableList<Snippet> = mutableListOf()

    fun requestBody(fields: List<FieldDescriptor>) {
        snippets.add(requestFields(fields))
    }

    fun requestBody(vararg fields: FieldDescriptor) {
        snippets.add(requestFields(*fields))
    }

    fun pathParams(fields: List<ParameterDescriptor>) {
        snippets.add(pathParameters(fields))
    }

    fun pathParams(vararg fields: ParameterDescriptor) {
        snippets.add(pathParameters(*fields))
    }

    fun queryParams(fields: List<ParameterDescriptor>) {
        snippets.add(queryParameters(fields))
    }

    fun queryParams(vararg fields: ParameterDescriptor) {
        snippets.add(queryParameters(*fields))
    }

    fun responseBody(fields: List<FieldDescriptor>) {
        snippets.add(responseFields(fields))
    }

    fun responseBody(vararg fields: FieldDescriptor) {
        snippets.add(responseFields(*fields))
    }

    fun build(): BodySpec<T, *> =
        contentSpec.consumeWith(
            WebTestClientRestDocumentationWrapper.document(
                identifier,
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                *snippets.toTypedArray(),
            ),
        )
}
