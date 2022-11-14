package com.example.workout_logger_presentation.start_workout

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
import java.util.prefs.Preferences
import javax.inject.Inject

@HiltViewModel
class StartWorkoutViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
//    private val preferences: Preferences,
    private val startWorkoutUseCases: ExerciseTrackerUseCases
): ViewModel() {

    var state by mutableStateOf(StartWorkoutState())
        private set

    private lateinit var workoutName: String

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getExerciseJob: Job? = null

    init {
        workoutName = savedStateHandle.get("workoutName") ?: ""
        getWorkout()
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
                            reps = it.reps.toString(),
                            origRest = it.rest.toString(),
                            rest = it.rest.toString(),
                            origWeight = it.weight.toString(),
                            weight = it.weight.toString(),
                            id = it.rowId,
                            exerciseId = it.exerciseId,
                            exercise = null,
                            isCompleted = false,
                            isInRestTimer = false
                        )
                    }.toMutableList()
                )
            }.launchIn(viewModelScope)
    }
}

//class StartWorkoutViewModelFactory(private val workoutName: String):
//        ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T = StartWorkoutViewModelFactory(workoutName) as T
//        }

