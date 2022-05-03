dependencies {
  implementation(project(":toolbox"))

  implementation("org.springframework:spring-tx")
  implementation("org.springframework:spring-web")
  implementation("org.springframework:spring-core")
  implementation("io.projectreactor:reactor-core")

  implementation("org.slf4j:slf4j-api")

  implementation("commons-io:commons-io")
  implementation("org.apache.commons:commons-lang3")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  testCompileOnly("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")
}
