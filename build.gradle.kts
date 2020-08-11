/*
 * Copyright (c) 2020 41North.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

if (!JavaVersion.current().isJava11Compatible) {
  throw GradleException("Java 11 or later is required to build this project. Detected version ${JavaVersion.current()}")
}

plugins {
  `maven-publish`
  distribution
  id("org.jetbrains.kotlin.jvm") version "1.3.72"
  id("com.github.johnrengelman.shadow") version "6.0.0"
  id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
  id("org.jlleitschuh.gradle.ktlint-idea") version "9.3.0"
  id("com.github.ben-manes.versions") version "0.29.0"
}

group = "io.besu.plugin"

allprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  repositories {
    jcenter()
    maven(url = "https://packages.confluent.io/maven/")
    maven(url = "https://dl.bintray.com/hyperledger-org/besu-repo/")
    maven(url = "https://dl.bintray.com/consensys/pegasys-repo/")
    maven(url = "https://repo.spring.io/libs-release")
  }

  tasks {
    val javaVersion = "11"

    withType<KotlinCompile>().all {
      sourceCompatibility = javaVersion
      targetCompatibility = javaVersion
      kotlinOptions.jvmTarget = javaVersion
    }

    withType<JavaCompile> {
      sourceCompatibility = javaVersion
      targetCompatibility = javaVersion
    }
  }

  ktlint {
    debug.set(false)
    verbose.set(true)
    outputToConsole.set(true)
    ignoreFailures.set(false)
    reporters {
      reporter(ReporterType.PLAIN)
    }
    filter {
      exclude("**/generated/**")
    }
  }
}

distributions {
  main {
    contents {
      from("LICENSE") { into("") }
      from("README.md") { into("") }
      from("plugin/build/libs") { into("plugins") }
    }
  }
}

tasks {
  jar { enabled = false }

  val distZip: Zip by container
  distZip.apply {
    doFirst { delete { fileTree(Pair("build/distributions", "*.zip")) } }
  }

  val distTar: Tar by container
  distTar.apply {
    doFirst { delete { fileTree(Pair("build/distributions", "*.tar.gz")) } }
    compression = Compression.GZIP
    archiveExtension.set("tar.gz")
  }

  withType<DependencyUpdatesTask> {
    fun isNonStable(version: String): Boolean {
      val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
      val regex = "^[0-9,.v-]+(-r)?$".toRegex()
      val isStable = stableKeyword || regex.matches(version)
      return isStable.not()
    }

    // Reject all non stable versions
    rejectVersionIf { isNonStable(candidate.version) }
  }
}
