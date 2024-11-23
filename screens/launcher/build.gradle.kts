plugins {
    id("focuslauncher.android.feature")
    id("focuslauncher.screen.new")
    id("focuslauncher.android.library.compose.testing")
}

android {
    namespace = "com.mohsen.clarityhub.screens.launcher"
}

dependencies {
    implementation(projects.feature.homepage)
    implementation(projects.feature.settingspage)
    implementation(projects.feature.appdrawerpage)
}
