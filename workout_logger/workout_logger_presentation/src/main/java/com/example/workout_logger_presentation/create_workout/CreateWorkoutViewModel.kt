package com.example.workout_logger_presentation.create_workout

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hbaez.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CreateWorkoutViewModel @Inject constructor(
//    private val createWorkoutUseCases: CreateWorkoutUseCases
): ViewModel() {

    var state by mutableStateOf(CreateWorkoutState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CreateWorkoutEvent) {
        when(event) {
            is CreateWorkoutEvent.OnAddExercise -> {
                addExercise()
            }
            is CreateWorkoutEvent.OnWorkoutNameChange -> {
                state = state.copy(workoutName = event.name)
            }

            is CreateWorkoutEvent.OnWorkoutNameFocusChange -> {
                state = state.copy(
                    isHintVisible = !event.isFocused && state.workoutName.isBlank()
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiNameChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            it.copy(name = event.name)
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiSetsChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            it.copy(sets = event.sets)
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiRepsChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            it.copy(reps = event.reps)
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiRestChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            it.copy(rest = event.rest)
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiWeightChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            it.copy(weight = event.weight)
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnDraggableRowExpand -> {
                Log.println(Log.DEBUG, "!!!!!!!!!!!!!!!!!!id", event.id.toString())
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if(it.id == event.id) {
                            it.copy(isRevealed = true)
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnDraggableRowCollapse -> {
                Log.println(Log.DEBUG, "!!!!!!!!!!!!!!!!!!id", event.id.toString())
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if(it.id == event.id){
                            it.copy(isRevealed = false)
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnRemoveTableRow -> {
                Log.println(Log.DEBUG, "!!!!!!!!!!!!!!!!!!id", event.id.toString())
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if(it.id == event.id){
                            it.copy(isDeleted = true)
                        } else it
                    }.toMutableList()
                )
//                val item = state.trackableExercises.find { it.id == event.id }
//                val index = state.trackableExercises.indexOf(item)
//                val tmpList = state.trackableExercises.toMutableList()
//                var tmpIndex = 0
//                var pastNull = false
//                tmpList.removeAt(index)
//                var currIndex = 0
//                state = state.copy(
////                    trackableExercises = state.trackableExercises.drop(state.trackableExercises.size)
//                    trackableExercises = tmpList
//                )
//                state = state.copy(
//                        trackableExercises = state.trackableExercises.toList().map {
//                            val tmpItem = tmpList[currIndex]
//                            currIndex += 1
//                            it.copy(
//                                    name = tmpItem.name,
//                                    sets = tmpItem.sets,
//                                    reps = tmpItem.reps,
//                                    rest = tmpItem.rest,
//                                    weight = tmpItem.weight,
//                                    id = tmpItem.id,
//                                    exercise = tmpItem.exercise,
//                                    isRevealed = tmpItem.isRevealed
//                                ) ?: it.copy()
//                        }.toMutableList()
////                    trackableExercises = state.trackableExercises.filter {
////                        it.id != event.id
////                    }
////                    trackableExercises = tmpList.toList()
//                )

            }
        }
    }

    private fun addExercise(){
        state = state.copy(
            trackableExercises = (state.trackableExercises.toList() + TrackableExerciseUiState(id = state.lastUsedId + 1, exercise = null)).toMutableList(),
            lastUsedId = state.lastUsedId + 1
        )
    }
}
