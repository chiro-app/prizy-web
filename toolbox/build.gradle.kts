dependencies {
  implementation("com.github.spullara.mustache.java:compiler")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  testCompileOnly("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")

  testImplementation("org.assertj:assertj-core")
  testImplementation("org.junit.jupiter:junit-jupiter")
}

