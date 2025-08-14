package com.gotchai.api.util

import com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper
import com.gotchai.api.docs.apiResponseFields
import com.gotchai.api.global.dto.ApiResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec
import kotlin.reflect.KProperty

typealias Field = Pair<String, String>

infix fun String.desc(description: String): Field = this to description

infix fun <T> KProperty<T>.desc(description: String): Field = name to description

fun fieldsOf(vararg fields: Field): List<Field> = fields.asList()

fun listFieldsOf(
    listField: Field,
    vararg fields: Field
): List<Field> = fields.map { "${listField.first}[].${it.first}" desc it.second } + listField

fun objectFieldsOf(
    objectField: Field,
    vararg fields: Field
): List<Field> = fields.map { "${objectField.first}.${it.first}" desc it.second } + objectField

fun <T> BodySpec<T, *>.document(
    identifier: String,
    init: (DocumentDsl<T>.() -> Unit)? = null
): BodySpec<T, *> =
    DocumentDsl(identifier, this)
        .apply { init?.let { it() } }
        .build()

class DocumentDsl<T>(
    private val identifier: String,
    private val contentSpec: BodySpec<T, *>
) {
    private val snippets: MutableList<Snippet> = mutableListOf()

    fun requestBody(fields: List<Field>) {
        snippets.add(
            requestFields(
                fields.map {
                    fieldWithPath(it.first)
                        .description(it.second)
                }
            )
        )
    }

    fun requestForm(fields: List<Field>) {
        snippets.add(
            requestParts(
                fields.map {
                    partWithName(it.first)
                        .description(it.second)
                }
            )
        )
    }

    fun pathParams(vararg fields: Field) {
        snippets.add(
            pathParameters(
                fields.map {
                    parameterWithName(it.first)
                        .description(it.second)
                }
            )
        )
    }

    fun queryParams(vararg fields: Field) {
        snippets.add(
            queryParameters(
                fields.map {
                    parameterWithName(it.first)
                        .description(it.second)
                }
            )
        )
    }

    fun responseBody(fields: List<Field>) {
        val mergedFields = mergeWithApiResponseFields(fields)

        snippets.add(
            responseFields(
                mergedFields.map {
                    fieldWithPath(it.first)
                        .description(it.second)
                }
            )
        )
    }

    fun build(): BodySpec<T, *> =
        contentSpec.consumeWith(
            WebTestClientRestDocumentationWrapper.document(
                identifier,
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                *snippets.toTypedArray()
            )
        )

    private fun mergeWithApiResponseFields(fields: List<Field>): List<Field> =
        fields.map { "${ApiResponse<*>::data.name}.${it.first}" to it.second } + apiResponseFields
}
