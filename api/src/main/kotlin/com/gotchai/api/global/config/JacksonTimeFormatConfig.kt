package com.gotchai.api.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Configuration
class JacksonTimeFormatConfig {
    @Bean
    fun objectMapper(): ObjectMapper =
        jacksonObjectMapper().registerModules(
            JavaTimeModule().apply {
                addSerializer(
                    LocalDate::class.java,
                    LocalDateSerializer(DateTimeFormatter.ISO_DATE),
                )
                addSerializer(
                    LocalDateTime::class.java,
                    LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")),
                )
                addSerializer(
                    ZonedDateTime::class.java,
                    ZonedDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
                )
                addSerializer(
                    LocalTime::class.java,
                    LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")),
                )
                addSerializer(
                    YearMonth::class.java,
                    YearMonthSerializer(DateTimeFormatter.ofPattern("yyyy-MM")),
                )
            },
        )
}
