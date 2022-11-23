package com.example.workout_logger_presentation.start_workout

import java.time.Duration

data class StartWorkoutState(
    val workoutName: String = "",
    val pagerIndex: Int = 0,
    val trackableInProgressExercise: MutableList<TrackableInProgressExerciseUi> = mutableListOf(),
    val timerStatus: TimerStatus = TimerStatus.START,
    val timeDuration: Duration = Duration.ofSeconds(30),
    val remainingTime: Long = timeDuration.toMillis(),
    val currRunningIndex: Int = -1
)

enum class TimerStatus {
    START, RUNNING, PAUSED, FINISHED
}
