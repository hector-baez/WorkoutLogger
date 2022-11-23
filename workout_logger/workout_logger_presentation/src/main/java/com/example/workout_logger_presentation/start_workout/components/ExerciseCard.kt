package com.example.workout_logger_presentation.start_workout.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.workout_logger_presentation.start_workout.TrackableInProgressExerciseUi
import com.hbaez.core_ui.LocalSpacing
import com.hbaez.workout_logger_presentation.R

@ExperimentalCoilApi
@Composable
fun ExerciseCard(
    page: Int,
    trackableInProgressExercise: TrackableInProgressExerciseUi,
    onRepsChange: (reps: String, index: Int, id: Int) -> Unit,
    onWeightChange: (weight: String, index: Int, id: Int) -> Unit,
    onCheckboxChange: (isCompleted: Boolean, index: Int, id: Int, page: Int) -> Unit
){
    val spacing = LocalSpacing.current
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .clip(
                RoundedCornerShape(50.dp)
            )
            .border(2.dp, MaterialTheme.colors.primary, RoundedCornerShape(50.dp))
            .padding(spacing.spaceMedium)
            .width(275.dp)
            .height(325.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text= stringResource(id = R.string.sets),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text= stringResource(id = R.string.weight),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text= stringResource(id = R.string.reps),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text= stringResource(id = R.string.completed_question),
                    style = MaterialTheme.typography.body2
                )
            }
            LazyColumn{
                itemsIndexed(List(trackableInProgressExercise.sets.toInt()) { it + 1 }){index, _ ->
                    ExerciseCardRow(
                        index + 1,
                        trackableInProgressExercise,
                        index,
                        onRepsChange,
                        onWeightChange,
                        onCheckboxChange,
                        page
                    )
                }
            }
        }
    }
}

@Composable
fun ExerciseCardRow(
    set: Int,
    trackableInProgressExercise: TrackableInProgressExerciseUi,
    index: Int,
    onRepsChange: (reps: String, index: Int, id: Int) -> Unit,
    onWeightChange: (weight: String, index: Int, id: Int) -> Unit,
    onCheckboxChange: (isCompleted: Boolean, index: Int, id: Int, page: Int) -> Unit,
    page: Int
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text= set.toString(),
            style = MaterialTheme.typography.body2
        )
        TextField(
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = trackableInProgressExercise.origWeight) },
            value = trackableInProgressExercise.weight[index],
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            singleLine = true,
            onValueChange = { onWeightChange(it, index, trackableInProgressExercise.id) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onDone = {
                    defaultKeyboardAction(ImeAction.Next)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        TextField(
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = trackableInProgressExercise.origReps) },
            value = trackableInProgressExercise.reps[index],
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            singleLine = true,
            onValueChange = { onRepsChange(it, index, trackableInProgressExercise.id) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onDone = {
                    defaultKeyboardAction(ImeAction.Next)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                focusedIndicatorColor = MaterialTheme.colors.primaryVariant,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Checkbox(
            modifier = Modifier.weight(1f),
            checked = trackableInProgressExercise.isCompleted[index],
            onCheckedChange = {
                onCheckboxChange(it, index, trackableInProgressExercise.id, page)
            }
        )

    }
}