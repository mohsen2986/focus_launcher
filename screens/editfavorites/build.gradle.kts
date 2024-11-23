plugins {
    id("focuslauncher.android.feature")
    id("focuslauncher.screen.new")
    id("focuslauncher.android.library.compose.testing")
}

android {
    namespace = "com.mohsen.clarityhub.screens.editfavorites"
}

dependencies {
    implementation(projects.core.ui)

    implementation(libs.kotlinx.collections.immutable)
}
