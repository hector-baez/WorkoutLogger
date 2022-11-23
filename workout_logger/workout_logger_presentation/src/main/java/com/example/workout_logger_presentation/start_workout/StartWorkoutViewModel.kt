package com.example.workout_logger_presentation.start_workout

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout_logger_domain.use_case.ExerciseTrackerUseCases
import com.hbaez.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import java.time.Duration
import java.util.concurrent.TimeUnit

@HiltViewModel
class StartWorkoutViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
//    private val preferences: Preferences,
    private val startWorkoutUseCases: ExerciseTrackerUseCases
): ViewModel() {

    var state by mutableStateOf(StartWorkoutState())
        private set

    var currentTime by mutableStateOf(state.remainingTime)
        private set

    private var workoutName: String

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getExerciseJob: Job? = null

    init {
        workoutName = savedStateHandle.get("workoutName") ?: ""
        getWorkout()
    }

    fun onEvent(event: StartWorkoutEvent) {
        when(event) {
            is StartWorkoutEvent.OnRepsChange -> {
                state = state.copy(
                    trackableInProgressExercise = state.trackableInProgressExercise.toList().map {
                        if(it.id == event.id){
                            val tmp = it.reps.toMutableList()
                            tmp[event.index]=event.reps
                            it.copy(reps = tmp.toList())
                        }else it
                    }.toMutableList()
                )
            }
            is StartWorkoutEvent.OnWeightChange -> {
                state = state.copy(
                    trackableInProgressExercise = state.trackableInProgressExercise.toList().map {
                        if(it.id == event.id){
                            val tmp = it.weight.toMutableList()
                            tmp[event.index]=event.weight
                            it.copy(weight = tmp.toList())
                        }else it
                    }.toMutableList()
                )
            }
            is StartWorkoutEvent.OnCheckboxChange -> {

                state = state.copy(
                    trackableInProgressExercise = state.trackableInProgressExercise.toList().map {
                        if(it.id == event.id){
                            val tmp = it.isCompleted.toMutableList()
                            tmp[event.index] = event.isChecked
                            it.copy(isCompleted = tmp, timerStatus = TimerStatus.RUNNING)
                        } else it
                    }.toMutableList(),
                    timerStatus = event.timerStatus,
                    pagerIndex = event.page,
                    timeDuration = Duration.ofSeconds(state.trackableInProgressExercise[event.page].origRest.toLong()),
                    currRunningIndex = event.currRunningIndex
                )
                currentTime = if(event.isChecked && event.timerStatus == TimerStatus.RUNNING) { state.timeDuration.seconds * 1000L } else currentTime
            }
            is StartWorkoutEvent.ChangeRemainingTime -> {
                currentTime -= 100L
            }
            is StartWorkoutEvent.UpdateRemainingTime -> {
                currentTime = currentTime
            }
            is StartWorkoutEvent.OnChangePage -> {
                Log.println(Log.DEBUG, "currentTime update", state.trackableInProgressExercise[event.currentPage].origRest)
                state = state.copy(
                    remainingTime = state.trackableInProgressExercise[event.currentPage].origRest.toLong(),
                    timeDuration = Duration.ofSeconds(state.trackableInProgressExercise[event.currentPage].origRest.toLong()),
                    pagerIndex = event.currentPage
                )
            }
            is StartWorkoutEvent.TimerFinished -> {
                state = state.copy(
                    timerStatus = TimerStatus.FINISHED
                )
            }
        }
    }

    private fun getWorkout(){
        getExerciseJob?.cancel()
        getExerciseJob = startWorkoutUseCases
            .getWorkoutsByName(workoutName)
            .onEach { exercises ->
                state = state.copy(
                    trackableInProgressExercise = exercises.map {
                        TrackableInProgressExerciseUi(
                            name = it.exerciseName,
                            origSets = it.sets.toString(),
                            sets = it.sets.toString(),
                            origReps = it.reps.toString(),
                            reps = List(it.sets) { _ -> it.reps.toString() },
                            origRest = it.rest.toString(),
                            rest = List(it.sets) { _ -> it.rest.toString() },
                            origWeight = it.weight.toString(),
                            weight = List(it.sets) { _ -> it.weight.toString() },
                            id = it.rowId,
                            exerciseId = it.exerciseId,
                            exercise = null,
                            isCompleted = List(it.sets) { false },
                            timerStatus = TimerStatus.START
                        )
                    }.toMutableList()
                )
            }.launchIn(viewModelScope)
    }
}
