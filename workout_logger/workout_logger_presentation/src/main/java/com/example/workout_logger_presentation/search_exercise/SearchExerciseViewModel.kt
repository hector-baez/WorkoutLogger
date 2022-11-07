package com.example.workout_logger_presentation.search_exercise

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout_logger_domain.use_case.ExerciseTrackerUseCases
import com.example.workout_logger_presentation.create_workout.TrackableExerciseUiState
import com.hbaez.core.util.UiEvent
import com.hbaez.core.util.UiText
import com.hbaez.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchExerciseViewModel @Inject constructor(
    private val searchExerciseUseCases: ExerciseTrackerUseCases
): ViewModel() {
    var state by mutableStateOf(SearchExerciseState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getExerciseJob: Job? = null

    fun onEvent(event: SearchExerciseEvent) {
        when(event) {
            is SearchExerciseEvent.OnExerciseNameChange -> {
                state = state.copy(
                    query = event.name
                )
            }

            is SearchExerciseEvent.OnExerciseNameFocusChange -> {
                /*TODO*/
            }

            is SearchExerciseEvent.OnSearch -> {
                executeSearch()
            }

            is SearchExerciseEvent.OnToggleTrackableExercise -> {
                state = state.copy(
                    trackableExercise = state.trackableExercise.map {
                        if(it.exercise.id == event.exercise.exercise.id){
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
        }
    }

    private fun executeSearch() {
        state = state.copy(
            query = state.query.trim()
        )
        getExerciseJob?.cancel()
        getExerciseJob = searchExerciseUseCases
            .getExerciseForName(state.query)
            .onEach { exercises ->
                if(exercises.isEmpty()){
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.empty_results)
                        )
                    )
                }
                state = state.copy(
                    trackableExercise = exercises.map {
                        TrackableExerciseState(exercise = it)
                    },
                    isSearching = false
                )
            }
            .launchIn(viewModelScope)

    }
}