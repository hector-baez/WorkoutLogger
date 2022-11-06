package com.example.workout_logger_presentation.search_exercise

import com.example.workout_logger_domain.model.TrackedExercise

data class SearchExerciseState(
    val query: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableExercise: List<TrackableExerciseState> = emptyList()
)
