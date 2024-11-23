plugins {
    id("focuslauncher.android.library")
    id("focuslauncher.android.library.compose")
    id("focuslauncher.android.library.compose.testing")
}

android {
    namespace = "com.mohsen.clarityhub.core.ui"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.resources)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.collections.immutable)
}
