plugins {
  kotlin("jvm")
  kotlin("kapt")
}

dependencies {
  implementation(project(":domain"))
  implementation(project(":toolbox"))

  implementation("org.springframework:spring-jdbc")
  implementation("org.springframework:spring-webflux")
  implementation("org.springframework.security:spring-security-oauth2-jose")
  implementation("org.springframework.security:spring-security-oauth2-resource-server")

  implementation("io.projectreactor.netty:reactor-netty-http")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  runtimeOnly("com.h2database:h2")

  implementation("org.assertj:assertj-db")
  implementation("io.rest-assured:xml-path")
  implementation("io.rest-assured:json-path")
  implementation("io.rest-assured:rest-assured")
  implementation("com.ekino.oss.jcv:jcv-hamcrest")

  implementation("org.mock-server:mockserver-netty")
  implementation("org.mock-server:mockserver-client-java")

  implementation("org.springframework.boot:spring-boot-starter-test")
  implementation("org.springframework.security:spring-security-test")

  implementation("com.fasterxml.jackson.core:jackson-core")
  implementation("com.fasterxml.jackson.core:jackson-databind")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
}
