package com.example.workout_logger_presentation.start_workout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.workout_logger_presentation.start_workout.components.Timer
import com.example.workout_logger_presentation.start_workout.components.ExerciseCard
import com.example.workout_logger_presentation.start_workout.components.NotificationUtil
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.hbaez.core_ui.LocalSpacing
import java.time.Duration

@RequiresApi(Build.VERSION_CODES.S)
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
    val pagerState = rememberPagerState(initialPage = 0)


    Scaffold(

        topBar = {
            Text(
                modifier = Modifier
                    .padding(spacing.spaceMedium),
                text = workoutName.uppercase(),
                style = MaterialTheme.typography.h2
            )
        },
        content = { padding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .scrollEnabled(state.timerStatus != TimerStatus.RUNNING),
                verticalAlignment = Alignment.CenterVertically,
                count = state.trackableInProgressExercise.size,
                contentPadding = PaddingValues(spacing.spaceSmall)
            ) {page ->
                if(pagerState.targetPage != pagerState.currentPage){
                    viewModel.onEvent(StartWorkoutEvent.OnChangePage(pagerState.targetPage))
                }
                Text(
                    text=state.trackableInProgressExercise[page].name,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.offset(y= -spacing.spaceLarge)
                )
                ExerciseCard(
                    page = page,
                    trackableInProgressExercise = state.trackableInProgressExercise[page],
                    onRepsChange = { reps, index, id ->
                        viewModel.onEvent(StartWorkoutEvent.OnRepsChange(reps = reps, index = index, id = id))
                    },
                    onWeightChange = { weight, index, id ->
                        viewModel.onEvent(StartWorkoutEvent.OnWeightChange(weight = weight, index = index, id = id))
                    },
                    onCheckboxChange = { isChecked, index, id, page ->
                        if(isChecked && state.currRunningIndex != index && state.timerStatus == TimerStatus.RUNNING){ // non checked clicked while timer already running
                            viewModel.onEvent(StartWorkoutEvent.OnCheckboxChange(isChecked= false, timerStatus = TimerStatus.RUNNING, currRunningIndex = state.currRunningIndex, index = index, id = id, page = page))
                        }
                        if(isChecked && (state.timerStatus == TimerStatus.START || state.timerStatus == TimerStatus.FINISHED)){ // non checked clicked while timer not running
                            viewModel.onEvent(StartWorkoutEvent.OnCheckboxChange(isChecked= true, timerStatus = TimerStatus.RUNNING, currRunningIndex = index, index = index, id = id, page = page))
                            val wakeupTime = StartWorkoutViewModel.setAlarm(context = context, timeDuration = Duration.ofSeconds(state.trackableInProgressExercise[page].origRest.toLong()))
                            NotificationUtil.showTimerRunning(context, wakeupTime)
                        }
                        else if(!isChecked && state.currRunningIndex == index && state.timerStatus == TimerStatus.RUNNING){ // checked clicked while that row has timer running
                            viewModel.onEvent(StartWorkoutEvent.OnCheckboxChange(isChecked= true, timerStatus = TimerStatus.FINISHED, currRunningIndex = -1, index = index, id = id, page = page))
                            StartWorkoutViewModel.removeAlarm(context)
                            NotificationUtil.hideTimerNotification(context)
                        }
                        else if(!isChecked && state.currRunningIndex != index){ // checked clicked while that row does not have timer running
                            viewModel.onEvent(StartWorkoutEvent.OnCheckboxChange(isChecked= false, timerStatus = state.timerStatus, currRunningIndex = state.currRunningIndex, index = index, id = id, page = page))
                        }
                    }
                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Timer(
                    modifier = Modifier
                        .size(200.dp),
                    pagerState = pagerState,
                    handleColor = MaterialTheme.colors.secondary,
                    inactiveBarColor = MaterialTheme.colors.primaryVariant,
                    activeBarColor = MaterialTheme.colors.primary
                )
            }
        }
    )

}

fun Modifier.scrollEnabled(
    enabled: Boolean,
) = nestedScroll(
    connection = object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource
        ): Offset = if(enabled) Offset.Zero else available
    }
)
