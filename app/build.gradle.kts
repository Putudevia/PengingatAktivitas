plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pengingataktivitas"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pengingataktivitas"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}



dependencies {
    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.1")

        // Lifecycle ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

        // Material Design
    implementation("com.google.android.material:material:1.11.0")
    }
