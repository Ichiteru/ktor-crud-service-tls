val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val h2_version: String by project
val hikari_version: String by project
val ktor_version: String by project

plugins {
  kotlin("jvm") version "2.0.10"
  id("io.ktor.plugin") version "2.3.12"
  id("org.jetbrains.kotlin.plugin.serialization") version "2.0.10"
}

group = "com.km"
version = "0.0.1"

application {
  mainClass.set("com.km.ApplicationKt")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
  mavenCentral()
}

//sourceSets {
//  main {
//    kotlin {
//      srcDir("src/main/kotlin")
//    }
//    resources {
//      srcDir("src/main/resources")
//    }
//  }
//}

dependencies {
  implementation("io.ktor:ktor-server-core-jvm")
  implementation("io.ktor:ktor-server-content-negotiation-jvm")
  implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
  implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
  implementation("com.h2database:h2:$h2_version")
  implementation("io.ktor:ktor-server-netty-jvm")
  implementation("io.ktor:ktor-server-status-pages:$ktor_version")
  implementation("io.ktor:ktor-server-swagger:$ktor_version")
  implementation("io.ktor:ktor-server-cors:$ktor_version")
  implementation("io.ktor:ktor-network-tls-certificates:$ktor_version")
  implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
  implementation("com.zaxxer:HikariCP:$hikari_version")
  implementation("ch.qos.logback:logback-classic:$logback_version")
  testImplementation("io.ktor:ktor-server-test-host-jvm")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
