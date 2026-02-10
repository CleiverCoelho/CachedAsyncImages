plugins {
    id("skeleton.android.feature")
}

android {
    namespace = "com.cleivercoelho.skeleton.feature.home"
}

dependencies {
    implementation(projects.core.data)
}
