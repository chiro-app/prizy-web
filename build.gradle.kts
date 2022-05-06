import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
  java
  kotlin("jvm") apply false
  id("io.spring.dependency-management")
  id("org.springframework.boot") apply false
}

group = "io.prizy"
version = file("version.txt").readText().trim()

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
    maven { url = uri("https://repo.spring.io/release") }
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
      val springBootAdminVersion: String by project

      dependency("de.codecentric:spring-boot-admin-starter-client:$springBootAdminVersion")
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

  tasks.withType<BootBuildImage> {
    builder = "paketobuildpacks/builder:tiny"
    environment = mapOf("BP_NATIVE_IMAGE" to "true")
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }

  tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
  }

}
