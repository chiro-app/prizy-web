plugins {
  java
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
  implementation("org.springframework.security:spring-security-crypto")

  implementation("org.flywaydb:flyway-core")
  implementation("com.vladmihalcea:hibernate-types-55")

  runtimeOnly("org.postgresql:postgresql")

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  testCompileOnly("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
}


tasks.withType<Test> {
  useJUnitPlatform()
}
