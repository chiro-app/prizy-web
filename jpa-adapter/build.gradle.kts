import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  kotlin("kapt")
  kotlin("plugin.jpa")
  kotlin("plugin.noarg")
  kotlin("plugin.spring")
  kotlin("plugin.allopen")
  id("org.springframework.boot")
  id("io.spring.dependency-management")
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

dependencies {
  implementation(project(":domain"))
  implementation(project(":toolbox"))

  implementation(kotlin("reflect"))

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

  implementation("org.flywaydb:flyway-core")

  runtimeOnly("org.postgresql:postgresql")

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
