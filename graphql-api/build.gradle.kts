import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
  id("org.springframework.boot")
  id("org.springframework.experimental.aot")
}

dependencies {
  implementation(project(":domain"))
  implementation(project(":adapter"))
  implementation(project(":toolbox"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-mail")
  implementation("org.springframework.boot:spring-boot-starter-graphql")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//  implementation("org.springframework.boot:spring-boot-starter-security")

//  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.security:spring-security-oauth2-jose")

  runtimeOnly("org.postgresql:postgresql")

  implementation("io.minio:minio")
  runtimeOnly("com.squareup.okhttp3:okhttp")

  implementation("org.flywaydb:flyway-core")
  implementation("com.vladmihalcea:hibernate-types-55")

  runtimeOnly("org.bouncycastle:bcprov-jdk15on")
  runtimeOnly("org.bouncycastle:bcpkix-jdk15on")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<BootJar> {
  archiveFileName.set("graphql-api.jar")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
  builder = "paketobuildpacks/builder:tiny"
  environment = mapOf("BP_NATIVE_IMAGE" to "true")
}
