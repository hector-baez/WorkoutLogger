package com.example.workout_logger_domain.repository

import com.example.workout_logger_domain.model.TrackedExercise
import com.example.workout_logger_domain.model.TrackedWorkout
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getExercisesForName(name: String): Flow<List<TrackedExercise>>

    suspend fun insertTrackedWorkout(trackedWorkout: TrackedWorkout)

    fun getWorkouts(): Flow<List<TrackedWorkout>>
}