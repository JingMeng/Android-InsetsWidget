plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.takwolf.android.insetswidget"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    compileOnly("androidx.core:core-ktx:1.8.0")
}

tasks {
    register("sourcesJar", Jar::class) {
        from(android.sourceSets["main"].java.srcDirs)
        archiveClassifier.set("sources")
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.takwolf.android.insetswidget"
                artifactId = "insetswidget"
                version = "0.0.1"

                from(components["release"])
                artifact(tasks.named("sourcesJar"))
            }
        }
    }
}
