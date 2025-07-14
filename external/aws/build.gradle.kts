dependencies {
    compileOnly(libs.spring.boot.starter.web)

    implementation(libs.bundles.aws.client)
    implementation(project(":core:core-domain"))
    implementation(project(":core:core-utils"))

    testImplementation(libs.spring.boot.starter.web)
}