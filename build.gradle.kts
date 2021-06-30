plugins {
    val kotlinVersion = "1.5.0"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.openapi.generator") version "5.1.1"
    id("org.springframework.boot") version "2.3.12.RELEASE"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.rameshkp.openapi-merger-gradle-plugin") version "1.0.4"
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

    // Swagger generated code
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.swagger:swagger-jersey2-jaxrs:1.6.2")

    // Logging
    implementation("io.github.microutils:kotlin-logging-jvm:${Version.kotlinLoggingJvm}")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
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

tasks.create<org.openapitools.generator.gradle.plugin.tasks.GenerateTask> ("external") {
    dependsOn("openApiGenerate")
    generatorName.set("spring")
    inputSpec.set("$rootDir/src/main/resources/external.yml")
    outputDir.set("$buildDir/generated/")
    templateDir.set("$rootDir/src/main/resources/swagger/")
    configFile.set("$rootDir/src/main/resources/api-config.json")
}

tasks.create<org.openapitools.generator.gradle.plugin.tasks.GenerateTask> ("petshop") {
    generatorName.set("spring")
    inputSpec.set("$rootDir/src/main/resources/petshop.yml")
    outputDir.set("$buildDir/generated/")
    configFile.set("$rootDir/src/main/resources/api-config.json")
}

openApiMerger {
    inputDirectory.set(file("$rootDir/src/main/resources/swagger/"))
    output {
        directory.set(file("$buildDir/generated/"))
        fileName.set("openapi")
        fileExtension.set("yaml")
    }
    openApi {
        openApiVersion.set("3.0.0")
        info {
            title.set("Petshop Merger")
            version.set("${project.version}")
        }
    }
}

tasks.getByName("openApiGenerate").dependsOn("mergeOpenApiFiles")

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$buildDir/generated/openapi.yaml")
    outputDir.set("$buildDir/generated/")
    configFile.set("$rootDir/src/main/resources/api-config.json")

    globalProperties.set( mapOf(
        Pair("apis", ""), //no value or comma-separated api names
        Pair("apiTests", "false"),
        Pair("models", ""), //no value or comma-separated api names
        Pair("modelTests", "false"),
        Pair("invoker", "false")
    ))
}

//java.sourceSets["main"].java.srcDir("$buildDir/generated/src/gen/java") //for jaxrs-spec
java.sourceSets["main"].java.srcDir("$buildDir/generated/src/main/java")

// Other way to add the java generated class to the source set

//configure<SourceSetContainer> {
//    named("main") {
//        java.srcDir("$buildDir/generated/src/gen/java")
//    }
//}

//sourceSets {
//    val main by getting
//    main.java.srcDir("$buildDir/generated/src/gen/java")
//}

//sourceSets["main"].withConvention(conventionType = org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
//    kotlin.srcDir("$buildDir/generated/src/gen/java")
//}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("openApiGenerate", "external", "petshop")
    //dependsOn( "petshop")
    kotlinOptions.jvmTarget = "11"
}

tasks.create<Copy>("copy") {
    into("$buildDir/")
    into("swagger/api/") {
        from("$buildDir/generated/openapi/")
    }
}
