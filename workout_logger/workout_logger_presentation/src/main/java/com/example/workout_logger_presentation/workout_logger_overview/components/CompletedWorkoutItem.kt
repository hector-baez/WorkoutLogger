package com.example.workout_logger_presentation.workout_logger_overview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.workout_logger_domain.model.CompletedWorkout
import com.hbaez.core_ui.LocalSpacing
import com.hbaez.workout_logger_presentation.R

@Composable
fun CompletedWorkoutItem(
    workout: CompletedWorkout,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /*TODO*/ }
                .padding(spacing.spaceMedium),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = workout.exerciseName,
                style = MaterialTheme.typography.h3
            )
        }
        Row(

        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = workout.reps.removeSurrounding("[","]").split(",").toList()[0],
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(id = R.string.reps),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Light
                )
            }
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = workout.rest.toString(),
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(id = R.string.rest),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}