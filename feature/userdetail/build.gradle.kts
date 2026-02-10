plugins {
    id("skeleton.android.feature")
}

android {
    namespace = "com.cleivercoelho.skeleton.feature.userdetail"
}

dependencies {
    implementation(projects.core.data)
}
