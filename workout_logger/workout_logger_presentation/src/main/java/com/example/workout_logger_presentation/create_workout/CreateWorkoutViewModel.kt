package com.example.workout_logger_presentation.create_workout

import androidx.lifecycle.ViewModel
import com.hbaez.core.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateWorkoutViewModel @Inject constructor(
    preference: Preferences,
//    private val createWorkoutUseCases: CreateWorkoutUseCases
): ViewModel() {

}