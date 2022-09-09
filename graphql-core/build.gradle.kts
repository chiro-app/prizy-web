plugins {
  kotlin("jvm")
  kotlin("kapt")
}

dependencies {
  implementation(project(":toolbox"))

  implementation(kotlin("reflect"))

  implementation("com.expediagroup:graphql-kotlin-spring-server")
  implementation("com.graphql-java:graphql-java-extended-validation")

  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.security:spring-security-oauth2-jose")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  implementation("co.elastic.clients:elasticsearch-java")
  implementation("com.fasterxml.jackson.core:jackson-databind")
  implementation("jakarta.json:jakarta.json-api:2.1.1")

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
