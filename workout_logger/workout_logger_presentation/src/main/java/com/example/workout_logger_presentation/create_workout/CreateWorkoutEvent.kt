package com.example.workout_logger_presentation.create_workout

import com.example.workout_logger_presentation.search_exercise.SearchExerciseEvent

sealed class CreateWorkoutEvent {

    object OnAddExercise: CreateWorkoutEvent()

    data class OnWorkoutNameChange(val name: String): CreateWorkoutEvent()

    data class OnWorkoutNameFocusChange(val isFocused: Boolean): CreateWorkoutEvent()

    data class OnTrackableExerciseUiNameChange(val name: String, val trackableExerciseUiState: TrackableExerciseUiState): CreateWorkoutEvent()

    data class OnTrackableExerciseUiSetsChange(val sets: String, val trackableExerciseUiState: TrackableExerciseUiState): CreateWorkoutEvent()

    data class OnTrackableExerciseUiRepsChange(val reps: String, val trackableExerciseUiState: TrackableExerciseUiState): CreateWorkoutEvent()

    data class OnTrackableExerciseUiRestChange(val rest: String, val trackableExerciseUiState: TrackableExerciseUiState): CreateWorkoutEvent()

    data class OnTrackableExerciseUiWeightChange(val weight: String, val trackableExerciseUiState: TrackableExerciseUiState): CreateWorkoutEvent()

    data class OnDraggableRowExpand(val id: Int): CreateWorkoutEvent()

    data class OnDraggableRowCollapse(val id: Int): CreateWorkoutEvent()

    data class OnDraggableRowCenter(val id: Int): CreateWorkoutEvent()

    data class OnRemoveTableRow(val id: Int): CreateWorkoutEvent()

    object CheckTrackedExercise: CreateWorkoutEvent()

}
