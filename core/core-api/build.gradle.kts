plugins {
    alias(libs.plugins.restdocs.api.spec)
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.aop)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.jakarta.validation)

    implementation(project(":core:core-domain"))
    implementation(project(":core:core-utils"))
    runtimeOnly(project(":storage:rds"))

    testImplementation(libs.bundles.spring.test)
    testImplementation(libs.bundles.spring.rest.docs)
    testImplementation(project(":storage:rds"))
    testFixturesImplementation(libs.bundles.test)
    testFixturesImplementation(libs.bundles.spring.rest.docs)
}

tasks {
    bootJar {
        enabled = true
    }

    jar {
        enabled = false
    }

    openapi3 {
        title = "Gotchai API"
        version = "v1"
        format = "yml"
        outputFileNamePrefix = "api"
        outputDirectory = "src/main/resources/static/docs"
    }
}
