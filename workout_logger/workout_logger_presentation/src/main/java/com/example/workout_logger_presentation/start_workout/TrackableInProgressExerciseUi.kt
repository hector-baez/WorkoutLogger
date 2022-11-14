package com.example.workout_logger_presentation.start_workout

import com.example.workout_logger_domain.model.TrackedExercise

data class TrackableInProgressExerciseUi(
    val name: String = "",
    val origSets: String = "",
    val sets: String = "",
    val origReps: String = "",
    val reps: String = "",
    val origRest: String = "",
    val rest: String = "",
    val origWeight: String = "",
    val weight: String = "",
    val id: Int = 0,
    val exerciseId: Int?,
    val exercise: TrackedExercise?,
    val isCompleted: Boolean,
    val isInRestTimer: Boolean
)
