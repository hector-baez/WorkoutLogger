package com.example.workout_logger_presentation.workout_logger_overview

import java.time.LocalDate

data class WorkoutLoggerOverviewState(
    val date: LocalDate = LocalDate.now()
)
