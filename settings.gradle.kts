pluginManagement {
  val kotlinVersion: String by settings
  val shadowVersion: String by settings
  val dgsCodegenVersion: String by settings
  val springBootVersion: String by settings
  val springAotVersion: String by settings
  val sdmVersion: String by settings

  plugins {
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.kapt") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("com.github.johnrengelman.shadow") version shadowVersion
    id("com.netflix.dgs.codegen") version dgsCodegenVersion
    id("org.springframework.boot") version springBootVersion
    id("org.springframework.experimental.aot") version springAotVersion
    id("io.spring.dependency-management") version sdmVersion
  }

  repositories {
    gradlePluginPortal()
    maven { url = uri("https://repo.spring.io/release") }
  }
}

rootProject.name = "prizy-web"

include("domain")
include("toolbox")
include("jpa-adapter")
include("public-api")
