import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    val kotlinVersion = "1.5.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
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
    implementation("org.springframework.data:spring-data-jpa")

    // Swagger generated code
    //implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    //implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.swagger:swagger-annotations:1.6.2")
    implementation("javax.validation:validation-api")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

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
    generatorName.set("jaxrs-spec")
    inputSpec.set("src/main/resources/petstore.yml")
    outputDir.set("$buildDir/generated")
    configFile.set("src/main/resources/api-config.json")
}

java {
    sourceSets["main"].apply {
        java.srcDir("$buildDir/generated/")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("openApiGenerate")
    kotlinOptions.jvmTarget = "11"
}
