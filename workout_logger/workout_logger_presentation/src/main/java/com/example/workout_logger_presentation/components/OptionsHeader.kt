package com.example.workout_logger_presentation.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hbaez.core.R
import com.hbaez.core_ui.LocalSpacing


@Composable
fun OptionsHeader(
    onNavigateToCreate: () -> Unit,
    modifier: Modifier = Modifier,
    displayWorkouts: () -> Unit
){
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                )
            )
            .background(MaterialTheme.colors.primary)
            .padding(
                vertical = spacing.spaceExtraLarge
            )
    ) {
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AddButton(
            text = "Start Workout",
            onClick = {
                      displayWorkouts()
                      },
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Default.AddCircle
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AddButton(
            text = "Create Workout",
            onClick = {
                onNavigateToCreate(

                )
            },
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Default.Build
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AddButton(text = "Edit Workout", onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(), icon = Icons.Default.Edit)
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
    }
}