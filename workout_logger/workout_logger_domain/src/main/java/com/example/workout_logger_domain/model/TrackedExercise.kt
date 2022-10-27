package com.example.workout_logger_domain.model

data class TrackedExercise(
    val id: Int? = null,
    val name: String,
    val exerciseBase: Int,
    val description: String? = null,
    val muscles: String? = null,
    val muscles_secondary: String? = null,
    val equipment: String? = null
)
