package com.gotchai

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class GotChaiApplication

fun main(args: Array<String>) {
    runApplication<GotChaiApplication>(*args)
}
