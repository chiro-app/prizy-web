plugins {
  kotlin("jvm") apply false
  kotlin("kapt") apply false
  kotlin("plugin.jpa") apply false
  kotlin("plugin.spring") apply false
  kotlin("plugin.allopen") apply false
  id("io.spring.dependency-management")
}

version = file("version.txt").readText().trim()
group = "io.prizy"

subprojects {
  version = rootProject.version
  group = rootProject.group

  apply {
    plugin("io.spring.dependency-management")
  }

  repositories {
    mavenCentral()
  }

  dependencyManagement {
    dependencies {

      val graphqlKotlinVersion: String by project
      val springBootVersion: String by project
      val mustacheVersion: String by project

      dependency("com.expediagroup:graphql-kotlin-spring-server:$graphqlKotlinVersion")
      dependency("com.github.spullara.mustache.java:compiler:$mustacheVersion")

      imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
      }

    }
  }
}
