package com.gotchai.api.util

import com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper
import com.gotchai.api.docs.apiResponseFields
import com.gotchai.api.global.dto.ApiResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec
import kotlin.reflect.KProperty

infix fun String.bodyDesc(description: String): FieldDescriptor =
    fieldWithPath(this)
        .description(description)

infix fun <T> KProperty<T>.bodyDesc(description: String): FieldDescriptor =
    fieldWithPath(name)
        .description(description)

infix fun String.paramDesc(description: String): ParameterDescriptor =
    parameterWithName(this)
        .description(description)

fun pathParamsOf(vararg param: ParameterDescriptor): List<ParameterDescriptor> = param.asList()

fun queryParamsOf(vararg param: ParameterDescriptor): List<ParameterDescriptor> = param.asList()

fun headerParamsOf(vararg param: ParameterDescriptor): List<ParameterDescriptor> = param.asList()

fun fieldsOf(vararg field: FieldDescriptor): List<FieldDescriptor> = field.asList()

fun listFieldsOf(
    description: String,
    vararg field: FieldDescriptor
): List<FieldDescriptor> =
    field.map { "list[].${it.path}" bodyDesc it.description as String } + ("list" bodyDesc description)

fun arrayFieldsOf(
    arrayName: String,
    description: String,
    vararg field: FieldDescriptor
): List<FieldDescriptor> =
    field.map { "$arrayName[].${it.path}" bodyDesc it.description as String } + (arrayName bodyDesc description)

fun nestedFieldsOf(
    parentPath: String,
    vararg field: FieldDescriptor
): List<FieldDescriptor> = field.map { "$parentPath.${it.path}" bodyDesc it.description as String }

fun optionalFieldsOf(vararg field: FieldDescriptor): List<FieldDescriptor> = field.map { it.optional() }

fun pageFieldsOf(
    contentDescription: String,
    vararg contentField: FieldDescriptor
): List<FieldDescriptor> =
    fieldsOf(
        "totalElements" bodyDesc "전체 요소 수",
        "totalPages" bodyDesc "전체 페이지 수",
        "size" bodyDesc "페이지 크기",
        "number" bodyDesc "현재 페이지 번호",
        "first" bodyDesc "첫 페이지 여부",
        "last" bodyDesc "마지막 페이지 여부",
        "empty" bodyDesc "빈 페이지 여부"
    ) + arrayFieldsOf("content", contentDescription, *contentField)

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
        snippets.add(responseFields(mergeWithApiResponseFields(fields)))
    }

    fun responseBody(vararg fields: FieldDescriptor) {
        snippets.add(responseFields(mergeWithApiResponseFields(fields.toList())))
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

    private fun mergeWithApiResponseFields(fields: List<FieldDescriptor>): List<FieldDescriptor> =
        apiResponseFields +
            fields.map {
                val path = "${ApiResponse<*>::data.name}.${it.path}"
                val description = it.description as String

                path bodyDesc description
            }
}
