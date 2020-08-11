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

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  `java-library`
  `maven-publish`
  kotlin("jvm")
  id("com.github.johnrengelman.shadow")
}

val besuVersion = "1.5.1"
val tuweniVersion = "1.0.0"
val picocliVersion = "4.2.0"

val pluginName = "besu-plugin"
val pluginVersion = "0.1.0"

dependencies {

  implementation(kotlin("stdlib"))

  // Official Besu Plugin API (https://besu.hyperledger.org/en/stable/Concepts/Plugins/)
  implementation("org.hyperledger.besu:plugin-api:$besuVersion")

  // Internal Besu packages ()
  // Optional: Remove these if you don't plan to work with these classes
  implementation("org.hyperledger.besu.internal:besu:$besuVersion")
  implementation("org.hyperledger.besu.internal:api:$besuVersion")
  implementation("org.hyperledger.besu.internal:config:$besuVersion")
  implementation("org.hyperledger.besu.internal:metrics-core:$besuVersion")
  implementation("org.hyperledger.besu.internal:kvstore:$besuVersion")

  // Tuweni (https://github.com/apache/incubator-tuweni/)
  // Optional: Remove these if you don't plan to work with these classes
  implementation("org.apache.tuweni:tuweni-bytes:$tuweniVersion")
  implementation("org.apache.tuweni:tuweni-units:$tuweniVersion")

  // PicoCLI (https://picocli.info/)
  // For working with CLI
  implementation("info.picocli:picocli:$picocliVersion")
}

tasks {
  val build: DefaultTask by container
  build.dependsOn(shadowJar)

  withType<ShadowJar> {
    archiveBaseName.set(pluginName)
    archiveVersion.set(pluginVersion)
    archiveClassifier.set("")
  }
}
