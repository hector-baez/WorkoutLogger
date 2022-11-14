package com.example.workout_logger_presentation.start_workout

data class StartWorkoutState(
    val workoutName: String = "",
    val pagerIndex: Int = 0,
    val trackableInProgressExercise: MutableList<TrackableInProgressExerciseUi> = mutableListOf()
)
