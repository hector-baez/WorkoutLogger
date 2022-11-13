package com.example.workout_logger_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackedWorkoutEntity(
    val workoutName: String,
    val workoutId: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    @PrimaryKey val id: Int? = null
)
