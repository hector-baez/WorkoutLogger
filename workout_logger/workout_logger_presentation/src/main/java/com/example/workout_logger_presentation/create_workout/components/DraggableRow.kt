package com.example.workout_logger_presentation.create_workout.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.workout_logger_presentation.create_workout.TrackableExerciseUiState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.consumeDownChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import com.example.workout_logger_presentation.create_workout.CreateWorkoutTableRow
import com.example.workout_logger_presentation.create_workout.CreateWorkoutViewModel
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

const val ANIMATION_DURATION = 1000
const val MIN_DRAG_AMOUNT = 6

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableRow(
    row: TrackableExerciseUiState,
    viewModel: CreateWorkoutViewModel,
    isRevealed: Boolean,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit
){
    val offsetX = remember { mutableStateOf(0f) }
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(targetState = transitionState, "rowTransition")
    val offsetTransition by transition.animateFloat(
        label = "rowOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) (cardOffset - offsetX.value) else 0f },
    )


    Row(
        modifier = Modifier
            .offset { IntOffset((offsetX.value.roundToInt() + offsetTransition).toInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    Log.println(Log.DEBUG, "dragAmount", dragAmount.toString())
                    val original = Offset(offsetX.value, 0f)
                    val summed = original + Offset(x = dragAmount, y = 0f)
                    val newValue = Offset(x = summed.x.coerceIn(0f, cardOffset), y = 0f)
                    when {
                        dragAmount > 10f -> onExpand()
                        dragAmount < -10f -> onCollapse()
                    }
//                    if (newValue.x >= 10) {
//                        onExpand()
//                        return@detectHorizontalDragGestures
//                    } else if (newValue.x <= 0) {
//                        onCollapse()
//                        return@detectHorizontalDragGestures
//                    }
                    change.consumePositionChange()
                    offsetX.value = newValue.x
                }
            }
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ){
        CreateWorkoutTableRow(
            trackableExerciseUiState = row,
            viewModel = viewModel,
        )
    }

}