package com.example.workout_logger_presentation.start_workout

sealed class StartWorkoutEvent {

    data class OnRepsChange(val reps: String, val index: Int, val id: Int): StartWorkoutEvent()

    data class OnWeightChange(val weight: String, val index: Int, val id: Int): StartWorkoutEvent()

    data class OnCheckboxChange(val isChecked: Boolean, val timerStatus: TimerStatus, val currRunningIndex: Int, val index: Int, val id: Int, val page: Int): StartWorkoutEvent()

    object ChangeRemainingTime: StartWorkoutEvent()

    object UpdateRemainingTime: StartWorkoutEvent()

    data class OnChangePage(val currentPage: Int): StartWorkoutEvent()

    object TimerFinished: StartWorkoutEvent()
}
