plugins {
  kotlin("jvm")
}

dependencies {
  implementation(project(":toolbox"))
  implementation(kotlin("stdlib"))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
}
