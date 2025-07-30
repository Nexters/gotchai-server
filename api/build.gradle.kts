import com.epages.restdocs.apispec.gradle.OpenApi3Task

plugins {
    alias(libs.plugins.restdocs.api.spec)
    alias(libs.plugins.jib)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common:util"))
    implementation(project(":infrastructure:oauth"))
    implementation(project(":storage:rdb"))
    implementation(project(":storage:redis"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.aop)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.jakarta.validation)
    implementation(libs.jjwt.api)
    runtimeOnly(libs.jjwt.jackson)
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(project(":common:logging"))

    testImplementation(testFixtures(project(":domain")))
    testImplementation(libs.bundles.spring.test)
    testImplementation(libs.bundles.spring.rest.docs)

    testFixturesImplementation(testFixtures(project(":common:util")))
    testFixturesImplementation(testFixtures(project(":domain")))
    testFixturesImplementation(libs.bundles.test)
    testFixturesImplementation(libs.bundles.spring.test)
    testFixturesImplementation(libs.bundles.spring.rest.docs)
    testFixturesImplementation(libs.spring.boot.starter.security)
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

    jib {
        from {
            image = "amazoncorretto:21-alpine"
        }
    }
}

openapi3 {
    title = "Gotchai API"
    version = "v1"
    format = "yml"
    outputFileNamePrefix = "api"
    outputDirectory = "src/main/resources/static/docs"
}

ext {
    set("openapi3OutDirectory", "src/main/resources/static/docs")
}
