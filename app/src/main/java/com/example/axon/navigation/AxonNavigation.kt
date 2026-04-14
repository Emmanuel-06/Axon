package com.example.axon.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.axon.AxonViewModel
import com.example.axon.screens.HomeScreen
import com.example.axon.screens.QuestionandAnswer
import com.example.axon.screens.Topics
import com.example.axon.util.Screens


@Composable
fun AxonNavigation() {
    val navController = rememberNavController()
    val viewModel: AxonViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screens.HOME.name
    ){
        composable(route = Screens.HOME.name){
            HomeScreen(
                onCategoryClick = { categoryName ->
                    navController.navigate(Screens.TOPICS.name + "/$categoryName")
                },
                listOfItems = viewModel.categories,
                viewModel = viewModel
            )
        }

        composable(
            route = Screens.TOPICS.name + "/{categoryName}",
            arguments = listOf(
                navArgument("categoryName"){ type = NavType.StringType}
            )
        ){navBackStackEntry ->
            Topics(
                axonViewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onTopicClick = { categoryName, topic->
                    navController.navigate(Screens.QUESTION_AND_ANSWER.name + "/$categoryName/$topic")
                },
                categoryId = navBackStackEntry.arguments?.getString("categoryName") ?: ""
            )
        }

        composable(
            route = Screens.QUESTION_AND_ANSWER.name + "/{categoryName}/{topic}",
            arguments = listOf(
                navArgument(name = "categoryName"){ type = NavType.StringType},
                navArgument(name = "topic"){ type = NavType.StringType}
            )
        ){navBackStackEntry ->

            QuestionandAnswer(
                axonViewModel = viewModel,
                categoryId = navBackStackEntry.arguments?.getString("categoryName") ?: return@composable,
                topicId = navBackStackEntry.arguments?.getString("topic") ?: return@composable
            ) {
                navController.popBackStack()

            }
        }
    }
}