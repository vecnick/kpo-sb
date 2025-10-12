plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "ru.dnd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.arrow.core)

    testImplementation(libs.kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}