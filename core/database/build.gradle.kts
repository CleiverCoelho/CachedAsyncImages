plugins {
    id("skeleton.android.library")
    id("skeleton.android.hilt")
    id("skeleton.android.room")
}

android {
    namespace = "com.cleivercoelho.skeleton.core.database"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.common)
}
