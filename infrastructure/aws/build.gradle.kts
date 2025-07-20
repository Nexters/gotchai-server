dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))

    compileOnly(libs.spring.boot.starter.web)
    implementation(libs.bundles.aws)

    testImplementation(libs.spring.boot.starter.web)
}
