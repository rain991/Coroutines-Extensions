import java.net.URI

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.dokka)
}

group = "io.github.kotlin"
version = "1.0.0"

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    signAllPublications()

    coordinates(group.toString(), "coroutines-extensions", version.toString())

    pom {
        name = "Coroutines extensions"
        description = "Helpful functions for Kotlin's coroutines and flow"

        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id = "rain991"
                name = "Ivan Savenko"
                url = "savenkoivan931@gmail.com"
            }
        }
        scm {
            url.set("https://github.com/rain991/Coroutines-Extensions")
            connection.set("scm:git:git://github.com/rain991/Coroutines-Extensions.git")
            developerConnection.set("scm:git:ssh://git@github.com:rain991/Coroutines-Extensions.git")
        }
    }
}

dokka {
    moduleName.set("Coroutines Extensions")

    dokkaSourceSets.commonMain{
        sourceLink {
            localDirectory.set(file("src/commonMain"))
            remoteUrl.set(URI("https://github.com/rain991/Coroutines-Extensions"))
        }
    }
}