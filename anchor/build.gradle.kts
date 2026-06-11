@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.android.build.gradle.internal.utils.createPublishingInfoForLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    id("maven-publish")
    id("com.vanniktech.maven.publish") version "0.36.0"
}

group = "com.stockgro.anchor"
version = "1.0.0"

kotlin {
    android {
        namespace = "com.stockgro.anchor"
        compileSdk {
            version = release(36) {
                minorApiLevel = 1
            }
        }
        minSdk = 24



        withHostTestBuilder {}.configure {  }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    val xcfName = "AnchorKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                api(libs.kotlinx.datetime)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {

            }
        }

        getByName("androidDeviceTest") {
            dependencies {
                implementation(libs.androidx.core)
                implementation(libs.androidx.runner)
                implementation(libs.androidx.testExt.junit)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }
}

publishing {
    publications {
        val kotlinMultiplatformPublication = publications.getByName("kotlinMultiplatform") as MavenPublication
        kotlinMultiplatformPublication.groupId = "com.stockgro"
        kotlinMultiplatformPublication.artifactId = "anchor"
        kotlinMultiplatformPublication.version = "1.0.0"
    }
    repositories {
        mavenLocal()
    }
}

publishing {
    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/Pranathi-StockGro/Anchor")
            credentials(PasswordCredentials::class)
        }
    }
}

mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        groupId = "com.stockgro",
        artifactId = "anchor",
        version = "1.0.0"
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("Utils library")
        description.set("Base Utils library to use accross different projects")
        inceptionYear.set("2026")
        url.set("https://github.com/Pranathi-StcokGro/Anchor")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        // Specify developers information
        developers {
            developer {
                id.set("Pranathi-StockGro")
                name.set("Pranathi")
                email.set("pranathi.pellakuru@stockgro.com")
            }
        }

        // Specify SCM information
        scm {
            url.set("https://github.com/Pranathi-StcokGro/Anchor")
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}