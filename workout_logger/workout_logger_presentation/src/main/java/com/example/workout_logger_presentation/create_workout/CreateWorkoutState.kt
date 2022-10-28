package com.example.workout_logger_presentation.create_workout

data class CreateWorkoutState(
    val workoutName: String = "",
    val isHintVisible: Boolean = false,
    val trackableExercises: List<TrackableExerciseUiState> = emptyList()
)