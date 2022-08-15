pluginManagement {
  val kotlinVersion: String by settings
  val springBootVersion: String by settings
  val sdmVersion: String by settings
  val releasePluginVersion: String by settings

  plugins {
    id("ch.netzwerg.release") version releasePluginVersion
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.kapt") version kotlinVersion
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version sdmVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
  }
}

rootProject.name = "prizy-web"

include("domain")
include("toolbox")
include("adapter")
include("test-core")
include("public-api")
include("graphql-core")
