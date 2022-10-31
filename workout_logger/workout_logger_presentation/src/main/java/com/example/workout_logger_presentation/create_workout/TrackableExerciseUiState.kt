package com.example.workout_logger_presentation.create_workout

import com.example.workout_logger_domain.model.TrackedExercise

data class TrackableExerciseUiState(
    val name: String = "",
    val sets: String = "0",
    val reps: String = "0",
    val rest: String = "0",
    val weight: String = "0",
    val id: Int = 0,
    val exercise: TrackedExercise?,
    val isRevealed: Boolean = false
)
