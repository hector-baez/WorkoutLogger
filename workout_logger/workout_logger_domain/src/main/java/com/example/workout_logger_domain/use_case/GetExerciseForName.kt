package com.example.workout_logger_domain.use_case

import com.example.workout_logger_domain.model.TrackedExercise
import com.example.workout_logger_domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow

class GetExerciseForName(
    private val repository: ExerciseRepository
) {

    operator fun invoke(name: String): Flow<List<TrackedExercise>> {
        return repository.getExercisesForName(name)
    }
}