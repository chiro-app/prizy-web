plugins {
  java
  id("io.spring.dependency-management")
}

version = file("version.txt").readText().trim()
group = "io.prizy"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

subprojects {
  version = rootProject.version
  group = rootProject.group

  apply {
    plugin("java")
    plugin("io.spring.dependency-management")
  }

  repositories {
    mavenCentral()
  }

  dependencyManagement {
    dependencies {

      val graphqlKotlinVersion: String by project
      val bouncyCastleVersion: String by project
      val springBootVersion: String by project
      val mustacheVersion: String by project

      dependency("com.expediagroup:graphql-kotlin-spring-server:$graphqlKotlinVersion")
      dependency("com.github.spullara.mustache.java:compiler:$mustacheVersion")
      dependency("org.bouncycastle:bcprov-jdk15on:$bouncyCastleVersion")
      dependency("org.bouncycastle:bcpkix-jdk15on:$bouncyCastleVersion")

      imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
      }

    }
  }

  tasks.withType(JavaCompile::class.java) {
    options.compilerArgs.add("--enable-preview")
  }
}
