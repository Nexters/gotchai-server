import com.epages.restdocs.apispec.gradle.OpenApi3Task

plugins {
    alias(libs.plugins.restdocs.api.spec)
    alias(libs.plugins.jib)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(project(":infrastructure:oauth"))
    implementation(project(":storage:rdb"))
    implementation(project(":storage:redis"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.aop)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.jakarta.validation)

    // Security
    implementation(libs.spring.boot.starter.security)
    testImplementation(libs.spring.security.test)
    implementation(libs.jjwt.api)
    runtimeOnly(libs.jjwt.jackson)
    runtimeOnly(libs.jjwt.impl)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.bundles.spring.test)
    testImplementation(libs.bundles.spring.rest.docs)
    testImplementation(testFixtures(project(":domain")))
    testFixturesImplementation(libs.bundles.test)
    testFixturesImplementation(libs.bundles.spring.test)
    testFixturesImplementation(libs.bundles.spring.rest.docs)
    testFixturesImplementation(testFixtures(project(":common")))
    testFixturesImplementation(testFixtures(project(":domain")))
    testImplementation(project(":storage:redis"))
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

    openapi3 {
        title = "Gotchai API"
        version = "v1"
        format = "yml"
        outputFileNamePrefix = "api"
        outputDirectory = "src/main/resources/static/docs"
    }
}
