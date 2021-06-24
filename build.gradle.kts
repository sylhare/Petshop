plugins {
    val kotlinVersion = "1.5.0"
    kotlin("jvm") version kotlinVersion
    id("org.openapi.generator") version "5.1.1"
    id("org.springframework.boot") version "2.3.12.RELEASE"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("jacoco")
    distribution
}

object Version {
    const val kotlinLoggingJvm = "2.0.8"
    const val junit = "5.7.2"
}

group = "com.github.petshop"
version = "1.0-SNAPSHOT"
description = "Example project for kotlin springboot and swagger code generation"
java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11


repositories {
    mavenCentral()
    maven(url = uri("https://plugins.gradle.org/m2/"))
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Spring Boot dependencies
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Logging
    implementation("io.github.microutils:kotlin-logging-jvm:${Version.kotlinLoggingJvm}")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test") {
        exclude(module = "junit")
        exclude(module = "junit-vintage-engine")
    }
    testImplementation("io.mockk:mockk:1.11.0")
    testImplementation("com.ninja-squad:springmockk:2.0.3")
    testImplementation("org.junit.jupiter:junit-jupiter:${Version.junit}")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

openApiGenerate {
    generatorName.set("kotlin")
    inputSpec.set("src/main/resources/petstore.yml")
    outputDir.set("$buildDir/generated")
    configFile.set("src/main/resources/api-config.json")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("openApiGenerate")
    kotlinOptions.jvmTarget = "11"
}
