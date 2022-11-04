package com.hbaez.workoutlogger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.workout_logger_presentation.create_workout.CreateWorkoutScreen
import com.example.workout_logger_presentation.workout_logger_overview.WorkoutLoggerOverviewScreen
import com.hbaez.workoutlogger.ui.theme.CaloryTrackerTheme
import com.hbaez.core.domain.preferences.Preferences
import com.hbaez.workoutlogger.navigation.Route
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()
        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.WORKOUT_OVERVIEW
                    ) {
//                        composable(Route.WELCOME) {
//                            WelcomeScreen(onNextClick = {
//                                navController.navigate(Route.GENDER)
//                            })
//                        }
//                        composable(Route.AGE) {
//                            AgeScreen(
//                                scaffoldState = scaffoldState,
//                                onNextClick = {
//                                    navController.navigate(Route.HEIGHT)
//                                }
//                            )
//                        }
//                        composable(Route.GENDER) {
//                            GenderScreen(onNextClick = {
//                                navController.navigate(Route.AGE)
//                            })
//                        }
//                        composable(Route.HEIGHT) {
//                            HeightScreen(
//                                scaffoldState = scaffoldState,
//                                onNextClick = {
//                                    navController.navigate(Route.WEIGHT)
//                                }
//                            )
//                        }
//                        composable(Route.WEIGHT) {
//                            WeightScreen(
//                                scaffoldState = scaffoldState,
//                                onNextClick = {
//                                    navController.navigate(Route.ACTIVITY)
//                                }
//                            )
//                        }
//                        composable(Route.NUTRIENT_GOAL) {
//                            NutrientGoalScreen(
//                                scaffoldState = scaffoldState,
//                                onNextClick = {
//                                    navController.navigate(Route.TRACKER_OVERVIEW)
//                                }
//                            )
//                        }
//                        composable(Route.ACTIVITY) {
//                            ActivityScreen(onNextClick = {
//                                navController.navigate(Route.GOAL)
//                            })
//                        }
//                        composable(Route.GOAL) {
//                            GoalScreen(onNextClick = {
//                                navController.navigate(Route.NUTRIENT_GOAL)
//                            })
//                        }
//
//                        composable(Route.TRACKER_OVERVIEW) {
//                            TrackerOverviewScreen(
//                                onNavigateToSearch = { mealName, day, month, year ->
//                                    navController.navigate(
//                                        Route.SEARCH + "/$mealName" +
//                                                "/$day" +
//                                                "/$month" +
//                                                "/$year"
//                                    )
//                                }
//                            )
//                        }

                        composable(Route.WORKOUT_OVERVIEW) {
                            WorkoutLoggerOverviewScreen(
                                onNavigateToCreate = {
                                    navController.navigate(
                                        Route.WORKOUT_CREATE
                                    )
                                }
                            )
                        }

                        composable(Route.WORKOUT_CREATE) {
                            CreateWorkoutScreen(
                                onNavigateToSearchExercise = {
                                    navController.navigate(Route.WORKOUT_SEARCH)
                                }
                            )
                        }

//                        composable(
//                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
//                            arguments = listOf(
//                                navArgument("mealName") {
//                                    type = NavType.StringType
//                                },
//                                navArgument("dayOfMonth") {
//                                    type = NavType.IntType
//                                },
//                                navArgument("month") {
//                                    type = NavType.IntType
//                                },
//                                navArgument("year") {
//                                    type = NavType.IntType
//                                },
//                            )
//                        ) {
//                            val mealName = it.arguments?.getString("mealName")!!
//                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
//                            val month = it.arguments?.getInt("month")!!
//                            val year = it.arguments?.getInt("year")!!
//                            SearchScreen(
//                                scaffoldState = scaffoldState,
//                                mealName = mealName,
//                                dayOfMonth = dayOfMonth,
//                                month = month,
//                                year = year,
//                                onNavigateUp = {
//                                    navController.navigateUp()
//                                }
//                            )
//                        }
                    }
                }

            }
        }
    }
}