dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
    runtimeOnly(project(":storage:rdb"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.aop)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.jakarta.validation)

    testImplementation(libs.spring.boot.starter.test)
}

tasks {
    bootJar {
        enabled = true
    }

    jar {
        enabled = false
    }
}
