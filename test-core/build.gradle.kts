dependencies {
  implementation(project(":toolbox"))
  implementation(project(":auth-core"))

  implementation("org.springframework:spring-jdbc")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  runtimeOnly("com.h2database:h2")

  implementation("org.assertj:assertj-db")
  implementation("io.rest-assured:xml-path")
  implementation("io.rest-assured:json-path")
  implementation("io.rest-assured:rest-assured")

  implementation("com.ekino.oss.jcv:jcv-assertj")
  implementation("com.ekino.oss.jcv:jcv-hamcrest")

  implementation("org.mock-server:mockserver-netty")
  implementation("org.mock-server:mockserver-client-java")

  implementation("org.springframework.boot:spring-boot-starter-test")
  implementation("org.springframework.security:spring-security-test")

  implementation("com.fasterxml.jackson.core:jackson-core")
  implementation("com.fasterxml.jackson.core:jackson-databind")

  implementation("com.icegreen:greenmail")
  implementation("com.icegreen:greenmail-junit5")
}
