package com.example.workout_logger_presentation.create_workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.workout_logger_presentation.components.AddButton
import com.example.workout_logger_presentation.components.NameField
import com.example.workout_logger_presentation.create_workout.components.DraggableRow
import com.hbaez.core_ui.LocalSpacing
import com.hbaez.workout_logger_presentation.R

@ExperimentalCoilApi
@Composable
fun CreateWorkoutScreen(
    viewModel: CreateWorkoutViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        NameField(
            text = state.workoutName,
            onValueChange = {
                            viewModel.onEvent(CreateWorkoutEvent.OnWorkoutNameChange(it))
            },
            onFocusChanged = {
                viewModel.onEvent(CreateWorkoutEvent.OnWorkoutNameFocusChange(it.isFocused))
            }
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        CreateWorkoutTableHeader()
        state.trackableExercises.map {
            Box(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)){
                Box(modifier = Modifier
                    .width(150.dp)
                    .fillMaxHeight()
                    .clip(RectangleShape)
                    .background(MaterialTheme.colors.error))
                DraggableRow(
                    row = it,
                    viewModel = viewModel,
                    isRevealed = it.isRevealed,
                    cardOffset = 400f,
                    onExpand = {
                        viewModel.onEvent(CreateWorkoutEvent.OnDraggableRowExpand(it.id))
                    },
                    onCollapse = {
                        viewModel.onEvent(CreateWorkoutEvent.OnDraggableRowCollapse(it.id))
                    }
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
        }
        AddButton(
            text = stringResource(id = R.string.add_exercise),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.onEvent(CreateWorkoutEvent.OnAddExercise)
            }
        )
    }
}