import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin("jvm") apply false
  id("io.spring.dependency-management")
  id("org.springframework.boot") apply false
  id("ch.netzwerg.release")
}

version = file("version.txt").readText().trim()

subprojects {
  version = rootProject.version
  group = rootProject.group

  apply {
    plugin("java")
    plugin("io.spring.dependency-management")
  }

  repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/release") }
  }

  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(17))
    }
  }

  dependencyManagement {
    dependencies {

      val minioVersion: String by project
      val okHttpVersion: String by project
      val mustacheVersion: String by project
      val commonsIOVersion: String by project
      val springBootVersion: String by project
      val graphqlJavaVersion: String by project
      val commonsLangVersion: String by project
      val bouncyCastleVersion: String by project
      val graphqlKotlinVersion: String by project
      val hibernateTypesVersion: String by project

      dependency("com.graphql-java:graphql-java-extended-validation:$graphqlJavaVersion")
      dependency("com.expediagroup:graphql-kotlin-spring-server:$graphqlKotlinVersion")
      dependency("com.github.spullara.mustache.java:compiler:$mustacheVersion")
      dependency("com.vladmihalcea:hibernate-types-55:$hibernateTypesVersion")
      dependency("org.apache.commons:commons-lang3:$commonsLangVersion")
      dependency("org.bouncycastle:bcprov-jdk15on:$bouncyCastleVersion")
      dependency("org.bouncycastle:bcpkix-jdk15on:$bouncyCastleVersion")
      dependency("com.squareup.okhttp3:okhttp:$okHttpVersion")
      dependency("commons-io:commons-io:$commonsIOVersion")
      dependency("io.minio:minio:$minioVersion")

      imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
      }

    }
  }

  configurations {
    compileOnly {
      extendsFrom(configurations.annotationProcessor.get())
    }
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = "17"
    }
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }

}

release {
  push = true
}