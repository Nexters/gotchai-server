allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    api(libs.spring.boot.starter.data.jpa)
    implementation(libs.bundles.line.kotlin.jdsl)
    compileOnly(project(":core:core-domain"))
    implementation(project(":core:core-utils"))

    runtimeOnly(libs.mysql.connector)
    runtimeOnly(libs.h2)

    testImplementation(project(":core:core-domain"))
}