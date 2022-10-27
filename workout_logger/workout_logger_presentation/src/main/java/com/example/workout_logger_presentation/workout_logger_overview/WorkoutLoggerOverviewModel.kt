package com.example.workout_logger_presentation.workout_logger_overview

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout_logger_domain.use_case.ExerciseTrackerUseCases
import com.hbaez.core.domain.preferences.Preferences
import com.hbaez.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class WorkoutLoggerOverviewModel @Inject constructor(
    preferences: Preferences,
    private val exerciseTrackerUseCases: ExerciseTrackerUseCases,
): ViewModel() {

    var state by mutableStateOf(WorkoutLoggerOverviewState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getWorkoutsForDateJob: Job? = null

    init {
        refreshWorkouts()
    }
    fun onEvent(event: WorkoutLoggerOverviewEvent) {
        when(event) {
            is WorkoutLoggerOverviewEvent.OnNextDayClick -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
            }
            is WorkoutLoggerOverviewEvent.OnPreviousDayClick -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
            }
        }
    }

    private fun refreshWorkouts(){
        getWorkoutsForDateJob?.cancel()
        Log.println(Log.DEBUG, "!!!!!!!!!!!!!!!!", "refreshing")
        getWorkoutsForDateJob = exerciseTrackerUseCases
            .getExerciseForName("Axe Hold")
            .onEach {
                Log.println(Log.DEBUG, "!!!!!!!!!!!!!!!!", it[0].description!!)
            }
            .launchIn(viewModelScope)
    }

}