plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.5"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.3.0")
    implementation("com.fasterxml.uuid:java-uuid-generator:5.0.0")
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.18")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.5")
}

detekt {
    config.setFrom("$rootDir/config/detekt.yml")
}

kotlin {
    jvmToolchain(17)
}
