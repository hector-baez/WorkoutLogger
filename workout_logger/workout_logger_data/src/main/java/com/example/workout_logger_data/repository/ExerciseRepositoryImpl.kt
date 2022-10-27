package com.example.workout_logger_data.repository

import com.example.workout_logger_data.local.ExerciseDao
import com.example.workout_logger_data.mapper.toTrackedExercise
import com.example.workout_logger_domain.model.TrackedExercise
import com.example.workout_logger_domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseRepositoryImpl(
    private val dao: ExerciseDao
): ExerciseRepository {

    override fun getExercisesForName(name: String): Flow<List<TrackedExercise>> {
        return dao.getExerciseByName(name).map { entities ->
            entities.map { it.toTrackedExercise() }

        }
    }

}