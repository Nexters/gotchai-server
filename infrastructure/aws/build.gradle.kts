dependencies {
    implementation(project(":domain"))
    implementation(project(":common:util"))

    implementation(libs.bundles.aws)
    compileOnly(libs.spring.boot.starter.web)

    testImplementation(libs.spring.boot.starter.web)
}
