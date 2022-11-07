package com.example.workout_logger_domain.repository

import com.example.workout_logger_domain.model.TrackedExercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getExercisesForName(name: String): Flow<List<TrackedExercise>>
}