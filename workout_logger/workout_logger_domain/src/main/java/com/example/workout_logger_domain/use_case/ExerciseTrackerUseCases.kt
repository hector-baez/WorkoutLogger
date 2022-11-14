package com.example.workout_logger_domain.use_case


data class ExerciseTrackerUseCases(
    val getExerciseForName: GetExerciseForName,
    val getWorkouts: GetWorkouts,
    val getWorkoutsByName: GetWorkoutsByName
)
