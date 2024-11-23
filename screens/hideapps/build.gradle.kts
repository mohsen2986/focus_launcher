plugins {
    id("focuslauncher.android.feature")
    id("focuslauncher.screen.new")
    id("focuslauncher.android.library.compose.testing")
}

android {
    namespace = "com.mohsen.clarityhub.screens.hideapps"
}

dependencies {
    implementation(libs.kotlinx.collections.immutable)
}
