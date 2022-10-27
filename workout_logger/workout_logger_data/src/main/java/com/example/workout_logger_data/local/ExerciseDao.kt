package com.example.workout_logger_data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workout_logger_data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercise(exerciseEntity: ExerciseEntity)

    @Query(
        """
            SELECT *
            FROM exerciseentity
            WHERE name = :name
        """
    )
    fun getExerciseByName(name: String): Flow<List<ExerciseEntity>>
}