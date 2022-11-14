package com.example.workout_logger_presentation.start_workout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.hbaez.core.R
import com.hbaez.core_ui.LocalSpacing

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@Composable
fun StartWorkoutScreen(
    workoutName: String,
    viewModel: StartWorkoutViewModel = hiltViewModel()
){
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .padding(spacing.spaceMedium),
                text = workoutName.uppercase(),
                style = MaterialTheme.typography.h2
            )
        },
        content = {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.spaceMedium),
                verticalAlignment = Alignment.CenterVertically,
                count = state.trackableInProgressExercise.size
            ) {
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
                    Text(
                        text=workoutName,
                        style = MaterialTheme.typography.h3
                        )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text=state.trackableInProgressExercise[0].name,
                            style = MaterialTheme.typography.h3
                        )
                    }
                }
            }
        }
    )

}