package com.example.workout_logger_presentation.create_workout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.hbaez.core_ui.LocalSpacing

@Composable
fun CreateWorkoutTableRow(
    trackableExerciseUiState: TrackableExerciseUiState,
    viewModel: CreateWorkoutViewModel
){
    val spacing = LocalSpacing.current

    Spacer(modifier = Modifier.height(spacing.spaceMedium))
    Row(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ){
        EditTableCell(
            trackableExerciseUiState =trackableExerciseUiState,
            text = trackableExerciseUiState.name,
            weight = .32f,
            keyboardType = KeyboardType.Text,
            onValueChange = {
                viewModel.onEvent(CreateWorkoutEvent.OnTrackableExerciseUiNameChange(it, trackableExerciseUiState))
            }
        )
        EditTableCell(
            trackableExerciseUiState =trackableExerciseUiState,
            text = trackableExerciseUiState.sets,
            weight = .16f,
            keyboardType = KeyboardType.Number,
            onValueChange = {
                if (it.isEmpty()){

                }
                viewModel.onEvent(CreateWorkoutEvent.OnTrackableExerciseUiSetsChange(it, trackableExerciseUiState))
            }
        )
        EditTableCell(
            trackableExerciseUiState =trackableExerciseUiState,
            text = trackableExerciseUiState.reps,
            weight = .16f,
            keyboardType = KeyboardType.Number,
            onValueChange = {
                viewModel.onEvent(CreateWorkoutEvent.OnTrackableExerciseUiRepsChange(it, trackableExerciseUiState))
            }
        )
        EditTableCell(
            trackableExerciseUiState =trackableExerciseUiState,
            text = trackableExerciseUiState.rest,
            weight = .18f,
            keyboardType = KeyboardType.Number,
            onValueChange = {
                viewModel.onEvent(CreateWorkoutEvent.OnTrackableExerciseUiRestChange(it, trackableExerciseUiState))
            }
        )
        EditTableCell(
            trackableExerciseUiState =trackableExerciseUiState,
            text = trackableExerciseUiState.weight,
            weight = .18f,
            keyboardType = KeyboardType.Number,
            onValueChange = {
                viewModel.onEvent(CreateWorkoutEvent.OnTrackableExerciseUiWeightChange(it, trackableExerciseUiState))
            }
        )

    }
    Spacer(modifier = Modifier.height(spacing.spaceMedium))
}

@Composable
fun RowScope.EditTableCell(
    trackableExerciseUiState: TrackableExerciseUiState,
    text: String,
    weight: Float,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit
){
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier
            .border(1.dp, MaterialTheme.colors.onBackground)
            .weight(weight)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        enabled = !trackableExerciseUiState.isRevealed
    )
}