pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.6"
    id("dev.kikugie.loom-back-compat") version "0.4"
}

rootProject.name = "crossbow_scoping"
loomx.loomVersion = "1.16-SNAPSHOT"

stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"

    // Subproject configuration
    create(rootProject) {
        versions("1.21.1", "1.21.8", "1.21.10", "1.21.11", "26.1.2", "26.2")
        vcsVersion = "1.21.1"
    }
}