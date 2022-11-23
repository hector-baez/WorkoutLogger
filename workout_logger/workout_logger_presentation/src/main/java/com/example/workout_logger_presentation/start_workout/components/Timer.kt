package com.example.workout_logger_presentation.start_workout.components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.workout_logger_presentation.start_workout.StartWorkoutEvent
import com.example.workout_logger_presentation.start_workout.StartWorkoutViewModel
import com.example.workout_logger_presentation.start_workout.TimerStatus
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.hbaez.workout_logger_presentation.R
import kotlinx.coroutines.delay
import java.lang.Math.PI
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalPagerApi::class)
@ExperimentalCoilApi
@Composable
fun Timer(
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp,
    viewModel: StartWorkoutViewModel = hiltViewModel()
    ){

    val context = LocalContext.current
    val state = viewModel.state
    Log.println(Log.DEBUG, "trackableIPExercise", state.trackableInProgressExercise.size.toString())
    val currentTime = viewModel.currentTime

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val angleRatio = remember {
        Animatable(1f)
    }
    LaunchedEffect(key1 = currentTime, key2 = state.timerStatus) {
        angleRatio.animateTo(
            targetValue = if (state.timeDuration.seconds > 0 && state.timerStatus == TimerStatus.RUNNING) {
                currentTime / (state.timeDuration.seconds * 1000).toFloat()
            } else 1f,
            animationSpec = tween(
                durationMillis = 300
            )
        )
    }
    LaunchedEffect(key1 = currentTime, key2 = state.timerStatus) {
        if(currentTime > 0 && state.timerStatus == TimerStatus.RUNNING) {
            delay(100L)
            viewModel.onEvent(StartWorkoutEvent.ChangeRemainingTime)
        }
        if(currentTime == 0L){
            viewModel.onEvent(StartWorkoutEvent.TimerFinished)
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .onSizeChanged {
                size = it
            },
    ) {
        // draw the timer
        Canvas(modifier = modifier) {
            // draw the inactive arc with following parameters
            drawArc(
                color = inactiveBarColor, // assign the color
                startAngle = -215f, // assign the start angle
                sweepAngle = 250f, // arc angles
                useCenter = false, // prevents our arc to connect at te ends
                size = Size(size.width.toFloat(), size.height.toFloat()),

                // to make ends of arc round
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            // draw the active arc with following parameters
            drawArc(
                color = activeBarColor, // assign the color
                startAngle = -215f,  // assign the start angle
                sweepAngle = 250f * angleRatio.value, // reduce the sweep angle
                // with the current value
                useCenter = false, // prevents our arc to connect at te ends
                size = Size(size.width.toFloat(), size.height.toFloat()),

                // to make ends of arc round
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            // calculate the value from arc pointer position
            val center = Offset(size.width / 2f, size.height / 2f)
            val beta = (250f * angleRatio.value + 145f) * (PI / 180f).toFloat()
            val r = size.width / 2f
            val a = cos(beta) * r
            val b = sin(beta) * r
            // draw the circular pointer/ cap
            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = handleColor,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round  // make the pointer round
            )
        }
        // add value of the timer
        Text(
            text = if (state.timerStatus == TimerStatus.RUNNING){ formatTime(currentTime / 1000L) } else { stringResource(R.string.null_time) },
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = if (state.timerStatus == TimerStatus.RUNNING){ Color.White } else { Color.Gray }
        )
    }
}

fun formatTime(currentTime: Long): String{
    val minutes = TimeUnit.MILLISECONDS.toMinutes(currentTime * 1000).toString().padStart(2,'0')
    val seconds = (TimeUnit.MILLISECONDS.toSeconds(currentTime * 1000)%60).toString().padStart(2,'0')
    return "${minutes}:${seconds}"
}