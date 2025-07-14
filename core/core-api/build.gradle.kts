tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.aop)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.jakarta.validation)

    implementation(project(":core:core-domain"))
    implementation(project(":core:core-utils"))
    runtimeOnly(project(":storage:rds"))

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(project(":storage:rds"))
}
