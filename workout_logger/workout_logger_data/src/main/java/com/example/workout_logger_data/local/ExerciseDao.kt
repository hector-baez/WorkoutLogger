package com.example.workout_logger_data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workout_logger_data.local.entity.ExerciseEntity
import com.example.workout_logger_data.local.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercise(exerciseEntity: ExerciseEntity)

    @Query(
        """
            SELECT *
            FROM exerciseentity
            WHERE name LIKE '%' || :name || '%' COLLATE NOCASE
        """
    )
    fun getExerciseByName(name: String): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workoutEntity: WorkoutEntity)

    @Query(
        """
            SELECT DISTINCT name, *
            FROM workoutentity
            GROUP BY name
        """
    )
    fun getWorkouts(): Flow<List<WorkoutEntity>>
}