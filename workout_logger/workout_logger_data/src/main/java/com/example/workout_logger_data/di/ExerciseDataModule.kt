package com.example.workout_logger_data.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.workout_logger_data.local.ExerciseDatabase
import com.example.workout_logger_data.repository.ExerciseRepositoryImpl
import com.example.workout_logger_domain.repository.ExerciseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExerciseDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideExerciseDatabase(app: Application): ExerciseDatabase {
        Log.println(Log.DEBUG, "!!!!!!!!!!!!!", "reached here")
        return Room.databaseBuilder(
            app,
            ExerciseDatabase::class.java,
            "exercise_db"
        )
            .createFromAsset("database/exercise.db")
//            .createFromAsset("database/muscles.tsv")
            .build()
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(
        db: ExerciseDatabase
    ): ExerciseRepository {
        return ExerciseRepositoryImpl(
            dao = db.dao
        )
    }

//    @Provides
//    @Singleton
//    fun provideTrackerDatabase(app: Application): TrackerDatabase {
//        return Room.databaseBuilder(
//            app,
//            TrackerDatabase::class.java,
//            "tracker_db"
//        ).build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideTrackerRepository(
//        api: OpenFoodApi,
//        db: TrackerDatabase
//    ): TrackerRepository {
//        return TrackerRepositoryImpl(
//            dao = db.dao,
//            api = api
//        )
//    }
}