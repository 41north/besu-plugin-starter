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
version = "0.1.0"

val distZip: Zip by project.tasks
distZip.apply {
  dependsOn(":plugin:build")
  doFirst { delete { fileTree(Pair("build/distributions", "*.zip")) } }
}

val distTar: Tar by project.tasks
distTar.apply {
  dependsOn("plugin:build")
  doFirst { delete { fileTree(Pair("build/distributions", "*.tar.gz")) } }
  compression = Compression.GZIP
  archiveExtension.set("tar.gz")
}

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
    withType<KotlinCompile>().all {
      sourceCompatibility = "11"
      targetCompatibility = "11"
      kotlinOptions.jvmTarget = "11"
    }

    withType<JavaCompile> {
      sourceCompatibility = "11"
      targetCompatibility = "11"
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

tasks {
  jar {
    enabled = false
  }

  withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    fun isNonStable(version: String): Boolean {
      val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
      val regex = "^[0-9,.v-]+(-r)?$".toRegex()
      val isStable = stableKeyword || regex.matches(version)
      return isStable.not()
    }

    // Reject all non stable versions
    rejectVersionIf {
      isNonStable(candidate.version)
    }
  }
}
