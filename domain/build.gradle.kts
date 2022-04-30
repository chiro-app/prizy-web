dependencies {
  implementation(project(":toolbox"))

  implementation("org.springframework:spring-tx")

  implementation("org.slf4j:slf4j-api")

  implementation("org.apache.commons:commons-lang3")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  testCompileOnly("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")
}
