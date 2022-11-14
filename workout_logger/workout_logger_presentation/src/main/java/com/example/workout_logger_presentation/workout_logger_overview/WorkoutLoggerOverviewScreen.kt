package com.example.workout_logger_presentation.workout_logger_overview

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.workout_logger_presentation.components.AddButton
import com.example.workout_logger_presentation.components.DaySelector
import com.example.workout_logger_presentation.components.OptionsHeader
import com.example.workout_logger_presentation.workout_logger_overview.components.WorkoutDialog
import com.hbaez.core.R
import com.hbaez.core_ui.LocalSpacing

@ExperimentalCoilApi
@Composable
fun WorkoutLoggerOverviewScreen(
    onNavigateToCreate: () -> Unit,
    onNavigateToWorkout: (workoutName: String) -> Unit,
    viewModel: WorkoutLoggerOverviewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            Log.println(Log.DEBUG, "showWorkoutDialog", state.showWorkoutDialog.toString())
            OptionsHeader(
                onNavigateToCreate,
                modifier = Modifier.fillMaxWidth(),
                displayWorkouts = {
                    viewModel.onEvent(WorkoutLoggerOverviewEvent.OnStartWorkoutClick)
                    showDialog.value = true
                }
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    viewModel.onEvent(WorkoutLoggerOverviewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    viewModel.onEvent(WorkoutLoggerOverviewEvent.OnNextDayClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            if(showDialog.value){
                Log.println(Log.DEBUG, "inside WorkoutDialog", state.workoutNames.toString())
                WorkoutDialog(
                    onDismiss = { showDialog.value = false },
                    onChooseWorkout = { onNavigateToWorkout(it) },
                    workoutNames = state.workoutNames
                )
            }
        }
    }
}