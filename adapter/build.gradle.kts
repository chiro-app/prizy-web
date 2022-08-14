dependencies {
  implementation(project(":domain"))
  implementation(project(":toolbox"))

  implementation("org.springframework.boot:spring-boot-starter-mail")
  implementation("org.springframework.security:spring-security-crypto")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

  implementation("io.minio:minio")
  runtimeOnly("com.squareup.okhttp3:okhttp")

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

  integrationTestCompileOnly("org.projectlombok:lombok")
  integrationTestAnnotationProcessor("org.projectlombok:lombok")

  integrationTestImplementation("org.springframework.boot:spring-boot-starter-test")
  integrationTestImplementation("org.springframework.security:spring-security-test")
}
