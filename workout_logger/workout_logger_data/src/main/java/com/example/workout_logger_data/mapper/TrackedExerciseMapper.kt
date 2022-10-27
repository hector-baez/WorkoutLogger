package com.example.workout_logger_data.mapper

import com.example.workout_logger_data.local.entity.ExerciseEntity
import com.example.workout_logger_domain.model.TrackedExercise

fun ExerciseEntity.toTrackedExercise(): TrackedExercise {
    return TrackedExercise(
        id = id,
        name = name,
        exerciseBase = exerciseBase,
        description = description,
        muscles = muscles,
        muscles_secondary = muscles_secondary,
        equipment = equipment
    )
}