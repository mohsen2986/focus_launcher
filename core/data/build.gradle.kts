plugins {
    id("focuslauncher.android.library")
    id("focuslauncher.android.hilt")
    id("focuslauncher.android.room")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mohsen.clarityhub.core.data"

    defaultConfig {
        testInstrumentationRunner = "com.mohsen.clarityhub.core.testing.HiltTestRunner"
    }
    sourceSets {
        getByName("test").assets.srcDir("$projectDir/schemas")
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.common)

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.serialization)

    implementation(libs.kotlinx.datetime)
    implementation(libs.suncalc)

    testImplementation(projects.core.testing)
    testImplementation(libs.room.testing)
}
