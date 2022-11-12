package com.example.workout_logger_presentation.create_workout

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.workout_logger_domain.model.TrackedExercise
import com.hbaez.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import com.hbaez.core.domain.preferences.Preferences
import javax.inject.Inject

@HiltViewModel
class CreateWorkoutViewModel @Inject constructor(
    val preferences: Preferences
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
                state = state.copy(
                    workoutName = if(event.name.trim().isNotEmpty() || event.name.isEmpty()){
                        event.name
                    } else state.workoutName
                )
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
                            if(event.name.trim().isNotEmpty() || event.name.isEmpty()){
                                it.copy(name = event.name)
                            } else it
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiSetsChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            if(event.sets.toIntOrNull() != 0 || event.sets.isEmpty()){
                                it.copy(sets = event.sets)
                            } else it.copy(sets = "")
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiRepsChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            if(event.reps.toIntOrNull() != 0 || event.reps.isEmpty()){
                                it.copy(reps = event.reps)
                            } else it.copy(reps = "")
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiRestChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            if(event.rest.toIntOrNull() != 0 || event.rest.isEmpty()){
                                it.copy(rest = event.rest)
                            } else it.copy(rest = "")
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiWeightChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            if(event.weight.toIntOrNull() != 0 || event.weight.isEmpty()){
                                it.copy(weight = event.weight)
                            } else it.copy(weight = "")
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnDraggableRowExpand -> {
                Log.println(Log.DEBUG, "onExpand", event.id.toString())
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if(it.id == event.id) {
                            it.copy(isRevealed = true, isSearchRevealed = false)
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnDraggableRowCollapse -> {
                Log.println(Log.DEBUG, "onCollapse", event.id.toString())
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if(it.id == event.id){
                            it.copy(isRevealed = true, isSearchRevealed = true)
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnDraggableRowCenter -> {
                Log.println(Log.DEBUG, "onCenter", event.id.toString())
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if(it.id == event.id){
                            it.copy(isRevealed = false, isSearchRevealed = false)
                        } else it
                    }.toMutableList()
                )
            }

            is CreateWorkoutEvent.OnRemoveTableRow -> {
                Log.println(Log.DEBUG, "onRemove", event.id.toString())
                state = state.copy(
                    trackableExercises = state.trackableExercises.toList().map {
                        if(it.id == event.id){
                            it.copy(isDeleted = true)
                        } else it
                    }.toMutableList()
                )

            }

            is CreateWorkoutEvent.CheckTrackedExercise -> {
                val trackedExercise = preferences.loadTrackedExercise()
                Log.println(Log.DEBUG, "create workout init", trackedExercise.name)
                if (trackedExercise.rowId != -1){
                    state = state.copy(
                        trackableExercises = state.trackableExercises.toList().map {
                            if(it.id == trackedExercise.rowId){
                                it.copy(
                                    name = trackedExercise.name,
                                    exercise = TrackedExercise(
                                        id = trackedExercise.id,
                                        name = trackedExercise.name,
                                        exerciseBase = trackedExercise.exerciseBase,
                                        description = trackedExercise.description,
                                        muscles = trackedExercise.muscles,
                                        muscles_secondary = trackedExercise.muscles_secondary,
                                        equipment = trackedExercise.equipment,
                                        image_url = trackedExercise.image_url.toList(),
                                        is_main = trackedExercise.is_main,
                                        is_front = trackedExercise.is_front,
                                        muscle_name_main = trackedExercise.muscle_name_main,
                                        image_url_main = trackedExercise.image_url_main.toList(),
                                        image_url_secondary = trackedExercise.image_url_secondary.toList(),
                                        muscle_name_secondary = trackedExercise.muscle_name_secondary
                                    )
                                )
                            } else it
                        }.toMutableList()
                    )
                    preferences.removeTrackedExercise()
                }
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
