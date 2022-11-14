package com.example.workout_logger_domain.di

import com.example.workout_logger_domain.repository.ExerciseRepository
import com.example.workout_logger_domain.use_case.AddWorkout
import com.example.workout_logger_domain.use_case.CreateWorkoutUseCases
import com.example.workout_logger_domain.use_case.ExerciseTrackerUseCases
import com.example.workout_logger_domain.use_case.GetExerciseForName
import com.example.workout_logger_domain.use_case.GetWorkouts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.prefs.Preferences

@Module
@InstallIn(ViewModelComponent::class)
object ExerciseDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        repository: ExerciseRepository
    ): ExerciseTrackerUseCases {
        return ExerciseTrackerUseCases(
            getExerciseForName = GetExerciseForName(repository),
            getWorkouts = GetWorkouts(repository)
        )
    }

    @ViewModelScoped
    @Provides
    fun provideCreateWorkoutUseCases(
        repository: ExerciseRepository
    ): CreateWorkoutUseCases {
        return CreateWorkoutUseCases(
            addWorkout = AddWorkout(repository)
        )
    }
}