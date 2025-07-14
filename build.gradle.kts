import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.java.library)
    alias(libs.plugins.java.test.fixtures)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management)
}

allprojects {
    group = "com.gotchai"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}

subprojects {
    val libs = rootProject.libs

    fun apply(provider: Provider<PluginDependency>): Unit = apply(plugin = provider.get().pluginId)

    apply(libs.plugins.java.library)
    apply(libs.plugins.java.test.fixtures)
    apply(libs.plugins.kotlin.jvm)
    apply(libs.plugins.kotlin.spring)
    apply(libs.plugins.spring.boot)
    apply(libs.plugins.spring.dependency.management)

    java {
        sourceCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JvmTarget.JVM_21
        }
    }

    dependencies {
        testImplementation(libs.bundles.test)
    }

    tasks {
        test {
            useJUnitPlatform()
        }
    }
}
