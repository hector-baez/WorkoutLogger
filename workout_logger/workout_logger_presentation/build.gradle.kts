apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.workoutLoggerDomain))

    "implementation"(Coil.coilCompose)

//    "implementation" ("androidx.lifecycle:lifecycle-viewmodel:2.5.1")
//    "implementation" ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

}