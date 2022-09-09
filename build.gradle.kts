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
    plugin("jvm-test-suite")
    plugin("io.spring.dependency-management")
  }

  repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/release") }
  }

  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(18))
    }
  }

  dependencyManagement {
    dependencies {

      val jcvVersion: String by project
      val minioVersion: String by project
      val okHttpVersion: String by project
      val mustacheVersion: String by project
      val commonsIOVersion: String by project
      val assertJDBVersion: String by project
      val springBootVersion: String by project
      val graphqlJavaVersion: String by project
      val commonsLangVersion: String by project
      val restAssuredVersion: String by project
      val bouncyCastleVersion: String by project
      val graphqlKotlinVersion: String by project
      val testContainersVersion: String by project
      val hibernateTypesVersion: String by project
      val elasticsearchVersion: String by project
      val awaitilityVersion: String by project
      val mockserverVersion: String by project
      val greenmailVersion: String by project

      dependency("com.graphql-java:graphql-java-extended-validation:$graphqlJavaVersion")
      dependency("com.expediagroup:graphql-kotlin-spring-server:$graphqlKotlinVersion")

      dependency("com.github.spullara.mustache.java:compiler:$mustacheVersion")

      dependency("com.vladmihalcea:hibernate-types-55:$hibernateTypesVersion")

      dependency("org.mock-server:mockserver-client-java:$mockserverVersion")

      dependency("org.testcontainers:testcontainers:$testContainersVersion")
      dependency("org.testcontainers:junit-jupiter:$testContainersVersion")
      dependency("org.testcontainers:postgresql:$testContainersVersion")

      dependency("org.apache.commons:commons-lang3:$commonsLangVersion")
      dependency("commons-io:commons-io:$commonsIOVersion")

      dependency("org.bouncycastle:bcprov-jdk15on:$bouncyCastleVersion")
      dependency("org.bouncycastle:bcpkix-jdk15on:$bouncyCastleVersion")

      dependency("io.rest-assured:rest-assured:$restAssuredVersion")
      dependency("io.rest-assured:json-path:$restAssuredVersion")
      dependency("io.rest-assured:xml-path:$restAssuredVersion")

      dependency("org.mock-server:mockserver-netty:$mockserverVersion")

      dependency("org.awaitility:awaitility:$awaitilityVersion")

      dependency("com.squareup.okhttp3:okhttp:$okHttpVersion")

      dependency("com.ekino.oss.jcv:jcv-hamcrest:$jcvVersion")
      dependency("com.ekino.oss.jcv:jcv-assertj:$jcvVersion")

      dependency("org.assertj:assertj-db:$assertJDBVersion")

      dependency("com.icegreen:greenmail:$greenmailVersion")
      dependency("com.icegreen:greenmail-junit5:$greenmailVersion")

      dependency("io.minio:minio:$minioVersion")

      dependency("co.elastic.clients:elasticsearch-java:$elasticsearchVersion")

      imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
      }

    }
  }

  testing {
    suites {
      val test by getting(JvmTestSuite::class) {
        useJUnitJupiter()
      }

      val integrationTest by registering(JvmTestSuite::class) {
        dependencies {
          implementation(project)
        }

        targets {
          all {
            testTask.configure {
              shouldRunAfter(test)
            }
          }
        }
      }
    }
  }

  tasks.named("check") {
    dependsOn(testing.suites.named("integrationTest"))
  }

  tasks.withType<JavaCompile> {
    options.encoding = "utf-8"
  }

  tasks.withType<Test> {
    systemProperties["file.encoding"] = "utf-8"
  }

  configurations {
    compileOnly {
      extendsFrom(configurations.annotationProcessor.get())
    }
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = "18"
    }
  }

}

release {
  push = true
}