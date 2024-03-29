import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
  kotlin("jvm")
  kotlin("kapt")
  kotlin("plugin.jpa")
  kotlin("plugin.noarg")
  kotlin("plugin.spring")
  kotlin("plugin.allopen")
  id("org.springframework.boot")
}

dependencies {
  implementation(project(":domain"))
  implementation(project(":adapter"))
  implementation(project(":toolbox"))
  implementation(project(":auth-core"))
  implementation(project(":graphql-core"))

  implementation(kotlin("reflect"))

  implementation("com.expediagroup:graphql-kotlin-spring-server")
  implementation("com.graphql-java:graphql-java-extended-validation")

  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-mail")

  implementation("io.projectreactor:reactor-core")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.security:spring-security-oauth2-jose")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

  runtimeOnly("org.postgresql:postgresql")

  runtimeOnly("org.bouncycastle:bcprov-jdk15on")
  runtimeOnly("org.bouncycastle:bcpkix-jdk15on")

  kapt("org.springframework.boot:spring-boot-configuration-processor")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")

  integrationTestImplementation(project(":test-core"))

  integrationTestCompileOnly(project(":domain"))
  integrationTestCompileOnly(project(":adapter"))

  integrationTestCompileOnly("org.projectlombok:lombok")
  integrationTestAnnotationProcessor("org.projectlombok:lombok")

  integrationTestRuntimeOnly("org.testcontainers:postgresql")

  integrationTestImplementation("org.awaitility:awaitility")
  integrationTestImplementation("org.mock-server:mockserver-netty")
  integrationTestImplementation("org.mock-server:mockserver-client-java")

  integrationTestImplementation("org.assertj:assertj-db")
  integrationTestImplementation("io.rest-assured:xml-path")
  integrationTestImplementation("io.rest-assured:json-path")
  integrationTestImplementation("io.rest-assured:rest-assured")

  integrationTestImplementation("org.springframework.boot:spring-boot-starter-test")
  integrationTestImplementation("org.springframework.security:spring-security-test")

  integrationTestImplementation("com.fasterxml.jackson.core:jackson-core")
  integrationTestImplementation("com.fasterxml.jackson.core:jackson-databind")

  integrationTestImplementation("org.testcontainers:junit-jupiter")
  integrationTestImplementation("org.testcontainers:testcontainers")
}

tasks.withType<BootJar> {
  archiveFileName.set("public-api.jar")
}
