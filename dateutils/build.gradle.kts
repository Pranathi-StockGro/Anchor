@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "com.stockgro.anchor"
version = "1.0.1"

kotlin {
    android {
        namespace = "com.stockgro.anchor.date"
        compileSdk {
            version = release(36) {
                minorApiLevel = 1
            }
        }
        minSdk = 24

        enableCoreLibraryDesugaring = true

        withHostTestBuilder {}.configure { }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

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
                implementation(libs.kotlinx.datetime)
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
    val props = gradleLocalProperties(rootDir, providers)
    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/stockgro-india/stockgro-util-library")
            credentials {
                username = props.getProperty("githubPackagesUsername")
                password = props.getProperty("githubPackagesPassword")
            }
        }
    }
}

//mavenPublishing {
//
//    // Configure POM metadata for the published artifact
//    pom {
//        name.set("date Utils library")
//        description.set("Base date Utils library to use accross different projects")
//        inceptionYear.set("2026")
//        url.set("https://github.com/stockgro-india/stockgro-util-library")
//
//        licenses {
//            license {
//                name.set("MIT")
//                url.set("https://opensource.org/licenses/MIT")
//            }
//        }
//
//        // Specify developers information
//        developers {
//            developer {
//                id.set("Pranathi-StockGro")
//                name.set("Pranathi")
//                email.set("pranathi.pellakuru@stockgro.com")
//            }
//        }
//
//        // Specify SCM information
//        scm {
//            url.set("https://github.com/stockgro-india/stockgro-util-library")
//        }
//    }
//}

dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}
