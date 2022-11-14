package com.example.workout_logger_presentation.workout_logger_overview

sealed class WorkoutLoggerOverviewEvent {
    object OnNextDayClick: WorkoutLoggerOverviewEvent()
    object OnPreviousDayClick: WorkoutLoggerOverviewEvent()
    object OnStartWorkoutClick: WorkoutLoggerOverviewEvent()
    object OnDismissDialog: WorkoutLoggerOverviewEvent()
    data class OnChooseWorkout(val workoutName: String): WorkoutLoggerOverviewEvent()
}
