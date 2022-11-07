package com.example.workout_logger_presentation.search_exercise.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import coil.annotation.ExperimentalCoilApi
import com.example.workout_logger_presentation.search_exercise.TrackableExerciseState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.hbaez.core_ui.LocalSpacing
import com.hbaez.core.R

@ExperimentalCoilApi
@Composable
fun TrackableExerciseItem(
    trackableExerciseState: TrackableExerciseState,
    onClick: () -> Unit,
    onTrack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val exercise = trackableExerciseState.exercise
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(spacing.spaceExtraSmall)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colors.surface)
            .clickable { onClick() }
//            .padding(end = spacing.spaceExtraSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Log.println(Log.DEBUG, "Image url", trackableExerciseState.exercise.image_url.toString())
            Image(
                painter = rememberImagePainter(
                    data = if (trackableExerciseState.exercise.image_url.isNotEmpty()) trackableExerciseState.exercise.image_url[0] else "",
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_exercise)
                        fallback(R.drawable.ic_exercise)
                    }
                ),
                contentDescription = exercise.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(topStart = 5.dp)),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Text(
                text = exercise.name!!,
                style = MaterialTheme.typography.body1,
                maxLines = 2,
                overflow = TextOverflow.Visible,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}