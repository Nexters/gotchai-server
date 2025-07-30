dependencies {
    implementation(project(":common"))

    compileOnly(libs.spring.context)
    compileOnly(libs.spring.tx)
    compileOnly(libs.jakarta.annotation.api)
    compileOnly(libs.spring.boot.starter.security)

    testImplementation(libs.spring.boot.starter.security)
    testFixturesImplementation(project(":common"))
}
