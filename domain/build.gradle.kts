dependencies {
    implementation(project(":common"))
    compileOnly(libs.spring.context)
    compileOnly(libs.spring.tx)
    compileOnly(libs.jakarta.annotation.api)
}
