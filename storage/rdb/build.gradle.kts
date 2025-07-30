plugins {
    alias(libs.plugins.kotlin.jpa)
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    compileOnly(project(":domain"))
    implementation(project(":common:util"))

    api(libs.spring.boot.starter.data.jpa)
    implementation(libs.bundles.jdsl)
    runtimeOnly(libs.mysql.connector)
    runtimeOnly(libs.h2)

    testImplementation(project(":domain"))
}
