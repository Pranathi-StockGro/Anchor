import java.util.Properties

rootProject.name = "BaseApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

val localPropertiesFile = settingsDir.resolve("local.properties")
val localProperties = Properties()

// 2. Safely read the file if it exists
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { stream ->
        localProperties.load(stream)
    }
}

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
//        maven {
//            name = "gitHubPackages"
//            url = uri("https://maven.pkg.github.com/Pranathi-StockGro/Anchor")
//            credentials {
//                username = localProperties.getProperty("githubPackagesUsername")
//                password = localProperties.getProperty("githubPackagesPassword")
//            }
//        }
        mavenLocal()
    }
}

include(":androidApp")
include(":shared")
include(":dateutils")
