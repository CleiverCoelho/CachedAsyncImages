plugins {
    id("skeleton.android.library")
    id("skeleton.android.compose")
}

android {
    namespace = "com.cleivercoelho.skeleton.core.ui"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(libs.coil.compose)
    implementation(libs.androidx.material3)
}
