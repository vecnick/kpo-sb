plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.freefair.lombok") version "8.6"
    jacoco
    java
    checkstyle
}

group = "hse.kpo.homework1"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

checkstyle {
    toolVersion = "10.13.0"
    isIgnoreFailures = false
    maxWarnings = 0
    maxErrors = 0
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}