package com.example.workout_logger_presentation.create_workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hbaez.core.domain.preferences.Preferences
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
                    trackableExercises = state.trackableExercises.map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            it.copy(name = event.name)
                        } else it
                    }
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiSetsChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            it.copy(sets = event.sets)
                        } else it
                    }
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiRepsChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            it.copy(reps = event.reps)
                        } else it
                    }
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiRestChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            it.copy(rest = event.rest)
                        } else it
                    }
                )
            }

            is CreateWorkoutEvent.OnTrackableExerciseUiWeightChange -> {
                state = state.copy(
                    trackableExercises = state.trackableExercises.map {
                        if (it.id == event.trackableExerciseUiState.id) {
                            it.copy(weight = event.weight)
                        } else it
                    }
                )
            }
        }
    }

    private fun addExercise(){
        state = state.copy(
            trackableExercises = state.trackableExercises + TrackableExerciseUiState(id = state.trackableExercises.size, exercise = null)
        )
    }

}