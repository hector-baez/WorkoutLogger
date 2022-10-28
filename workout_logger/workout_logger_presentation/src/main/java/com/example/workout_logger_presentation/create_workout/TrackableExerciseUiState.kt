package com.example.workout_logger_presentation.create_workout

import com.example.workout_logger_domain.model.TrackedExercise

data class TrackableExerciseUiState(
    val name: String = "",
    val sets: Int = 0,
    val reps: Int = 0,
    val rest: Int = 0,
    val weight: Int = 0,
    val id: Int = 0,
    val exercise: TrackedExercise?
)
