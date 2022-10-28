package com.example.workout_logger_presentation.create_workout

sealed class CreateWorkoutEvent {

    object OnAddExercise: CreateWorkoutEvent()

    data class OnWorkoutNameChange(val name: String): CreateWorkoutEvent()

    data class OnWorkoutNameFocusChange(val isFocused: Boolean): CreateWorkoutEvent()
}
