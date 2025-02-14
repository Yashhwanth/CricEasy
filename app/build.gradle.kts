plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.cricketscoringapp.criceasy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cricketscoringapp.criceasy"
        minSdk = 24
        targetSdk = 34
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
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.legacy.support.v4)

    // Google Play Services
    implementation(libs.play.services.maps)
    implementation(libs.play.services.places)
    implementation(libs.places)

    // RecyclerView and CardView
    implementation(libs.recyclerview)
    implementation(libs.cardview)

    // Room dependencies
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.mockito.inline)  // For mocking final methods
    implementation(libs.core) // or the latest version
    implementation(libs.junit.v115) // JUnit extension
    implementation(libs.runner) // Test runner
    implementation(libs.rules) // Test rules
    implementation(libs.espresso.core.v350) // Espresso for UI tests
    testImplementation(libs.junit) // JUnit for unit tests

}