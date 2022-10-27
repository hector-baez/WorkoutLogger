package com.example.workout_logger_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MuscleEntity(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val name_en: String?,
    val is_front: Boolean,
    val image_url_main: String,
    val image_url_secondary: String
)
