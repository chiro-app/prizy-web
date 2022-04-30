import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  kotlin("kapt")
  id("io.spring.dependency-management")
}

dependencies {
  implementation(project(":toolbox"))

  implementation(kotlin("reflect"))

  implementation("com.expediagroup:graphql-kotlin-spring-server")

  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.security:spring-security-oauth2-jose")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  implementation("de.slub-dresden:urnlib:2.0.1")

  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

  runtimeOnly("org.postgresql:postgresql")

  runtimeOnly("org.bouncycastle:bcprov-jdk15on")
  runtimeOnly("org.bouncycastle:bcpkix-jdk15on")

  kapt("org.springframework.boot:spring-boot-configuration-processor")

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