<h1 align="center">⚡ Besu Plugin Starter ⚡</h1>

<p align="center">Kick-start your next Besu plugin!</p>

## 💡 Introduction

Do you want to create a [Besu plugin](https://besu.hyperledger.org/en/stable/Concepts/Plugins/) super fast? This template will help you get started!

What is included:

- generate fat `JARs` to distribute the plugin with ease.
- easily check if all the various Besu dependencies are up to date.
- ensure a consistent coding style with auto-formatting.

Whilst this project is mainly geared towards Kotlin it can still be used with plain old Java.

Bundled Gradle plugins:

- [`ShadowJar`](https://github.com/johnrengelman/shadow) - For creating fat `JARs`.
- [`Ktlint`](https://github.com/JLLeitschuh/ktlint-gradle) - For automatic formatting of Kotlin code.
- [`Gradle Versions Plugin`](https://github.com/ben-manes/gradle-versions-plugin) - Determine which dependencies have updates

## 🙈 Usage

Simply fork this repository and start hacking away!

Below is a summary of some useful `gradle` tasks that you will have at your disposal:

| Target            | Description                                                     |
|-------------------|-----------------------------------------------------------------
| assemble          | Full `JAR` file in `build/distributions` as `.jar`.
| assembleDist      | Creates `.zip` and `.tar` archives of the distribution contents.
| distZip           | Full `JAR` file distribution in `build/distributions` as `.zip`.
| distTar           | Full `JAR` file distribution in `build/distributions` as `.tar`.
| ktlintFormat      | Formats automatically the code.
| dependencyUpdates | Check if all dependencies are up to date.
| tasks             | Display all available tasks.


## 💻 Contribute

We welcome any kind of contribution or support to this project but before to do so:

* Make sure you have read the [contribution guide](/.github/CONTRIBUTING.md) for more details on how to submit a good PR (pull request).

Also, we are not only limited to technical contributions. Things that make us happy are:

* Add a [GitHub Star](https://github.com/41north/besu-plugin-starter/stargazers) to the project.
* Tweet about this project.
* Write a review or tutorial.

## Other Gradle plugins

We have published other Besu plugins:

- [Besu Exflo](https://github.com/41north/besu-exflo).
- [Besu Storage Replication](https://github.com/41north/besu-storage-replication).

Also, have a look at our [Awesome Besu](https://github.com/41north/awesome-besu) list to find more useful stuff!

## 📬 Get in touch

`Besu Plugin Starter` has been developed initially by [°41North](https://41north.dev). 

If you think this project would be useful for your use case and want to talk more about it you can reach out to us via 
our contact form or by sending an email to `hello@41north.dev`. We try to respond within 48 hours and look forward to hearing from you.

## ✍️ License

`Besu Plugin Starter` is free and open-source software licensed under the [Apache 2.0 License](./LICENSE).
