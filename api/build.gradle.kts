import com.epages.restdocs.apispec.gradle.OpenApi3Task

plugins {
    alias(libs.plugins.restdocs.api.spec)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
    runtimeOnly(project(":storage:rdb"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.aop)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.jakarta.validation)

    // Security
    implementation(libs.spring.boot.starter.security)
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    testImplementation(libs.spring.security.test)
    implementation(libs.jjwt.api)
    runtimeOnly(libs.jjwt.jackson)
    runtimeOnly(libs.jjwt.impl)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.bundles.spring.rest.docs)
    testFixturesImplementation(libs.bundles.test)
    testFixturesImplementation(libs.bundles.spring.test)
    testFixturesImplementation(libs.bundles.spring.rest.docs)
}

tasks {
    bootJar {
        enabled = true
    }

    jar {
        enabled = false
    }

    test {
        finalizedBy(withType<OpenApi3Task>())
    }

    openapi3 {
        title = "Gotchai API"
        version = "v1"
        format = "yml"
        outputFileNamePrefix = "api"
        outputDirectory = "src/main/resources/static/docs"
    }
}
