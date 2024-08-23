import org.gradle.api.JavaVersion.VERSION_21
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    val kotlinVersion = "2.0.20"

    id("com.github.ben-manes.versions") version "0.51.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.springframework.boot") version "3.3.3"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

group = "de.predic8"
version = "1.0.0"

java {
    sourceCompatibility = VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    // Database
    runtimeOnly("org.hsqldb:hsqldb")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // (De-)Compression
    implementation("org.apache.commons:commons-compress:1.27.1")

    // (De-)Serialization
    implementation("com.charleskorn.kaml:kaml:0.61.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")

    // Git
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.10.0.202406032230-r")
    implementation("org.kohsuke:github-api:1.324")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // Open the browser on startup
    implementation("org.seleniumhq.selenium:selenium-java:4.23.1")

    // OS detection
    implementation("org.apache.commons:commons-lang3:3.16.0")

    // Scheduler
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC.2")

    // Versioning
    implementation("org.semver4j:semver4j:5.3.0")

    // Web
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.named<BootJar>("bootJar") {
    archiveFileName = "openapi-catalog-${version}.jar"

    launchScript()
}

tasks.named("compileKotlin", KotlinCompilationTask::class.java) {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
