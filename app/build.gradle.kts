import org.gradle.internal.deprecation.DeprecationMessageBuilder.DeprecateNamedParameter

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("kotlinx-serialization")

   // id( "com.hiya.jacoco-android")

    // id("kotlin-android-extensions")

    //id("kotlin-android")

    //id("com.github.triplet.play")
    //id("com.github.triplet.play") version "2.0.0"


}


android {
    namespace = "hu.andersen.weather_station"
    compileSdk = 34

    defaultConfig {
        applicationId = "hu.andersen.weather_station"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }



}

dependencies {

    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))

    implementation("androidx.compose.material3:material3:1.2.1")

    implementation(project(":linegraph"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    implementation ("io.ktor:ktor-client-android:1.5.0")
    implementation ("io.ktor:ktor-client-json:1.5.0")
    implementation ("io.ktor:ktor-client-serialization:1.5.0")
    implementation ("io.ktor:ktor-client-logging:1.5.0")
    //implementation("io.ktor:ktor-client-cio:1.5.0")

    implementation("io.ktor:ktor-client-auth:1.5.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
  //  implementation ("io.github.farshidroohi:lineGraph:1.0.2")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
// Charts
    implementation ("com.diogobernardino:williamchart:3.11.0")

// Tooltips
    implementation ("com.diogobernardino.williamchart:tooltip-slider:3.11.0")
    implementation ("com.diogobernardino.williamchart:tooltip-points:3.11.0")

    //implementation 'com.diogobernardino.williamchart:tooltip-slider:3.10.1'
    //implementation 'com.diogobernardino.williamchart:tooltip-points:3.10.1'
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")


}