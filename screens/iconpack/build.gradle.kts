plugins {
    id("focuslauncher.android.feature")
    id("focuslauncher.screen.new")
    id("focuslauncher.android.library.compose.testing")
}

android {
    namespace = "com.mohsen.clarityhub.screens.iconpack"
}

dependencies {
    implementation(projects.feature.appdrawerpage)

    implementation(libs.kotlinx.collections.immutable)
}
