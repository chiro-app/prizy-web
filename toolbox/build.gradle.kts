plugins {
  kotlin("jvm")
  kotlin("plugin.allopen")
}

dependencies {
  implementation(kotlin("stdlib"))

  implementation("com.github.spullara.mustache.java:compiler")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

  testImplementation("org.junit.jupiter:junit-jupiter")
}

