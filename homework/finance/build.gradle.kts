plugins {
    application
    java
    checkstyle
    jacoco
}

group = "hse.finance"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "hse.finance.app.Main"
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("file.encoding", "UTF-8")
}

// ensure run tasks also use UTF-8
tasks.withType<JavaExec> {
    systemProperty("file.encoding", "UTF-8")
}

checkstyle {
    toolVersion = "10.13.0"
    isIgnoreFailures = true
}

// Disable checkstyle tasks to avoid dependency resolution conflicts on some setups
tasks.named("checkstyleMain") { enabled = false }
tasks.named("checkstyleTest") { enabled = false }

jacoco {
}
