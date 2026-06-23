package com.example.axon.navigation

//import com.example.axon.screens.Topics
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.axon.AxonViewModel
import com.example.axon.presentation.screens.HomeScreen
import com.example.axon.presentation.screens.QuestionAndAnswer
import com.example.axon.presentation.screens.Topics
import com.example.axon.util.Screens


@Composable
fun AxonNavigation() {
    val navController = rememberNavController()
    val viewModel: AxonViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screens.HOME.name
    ) {
        composable(route = Screens.HOME.name) {
            HomeScreen(
                onCategoryClick = { categoryName ->
                    navController.navigate(Screens.TOPICS.name + "/$categoryName")
                },
                axonViewModel = viewModel
            )
        }

        composable(
            route = Screens.TOPICS.name + "/{categoryName}",
            arguments = listOf(
                navArgument("categoryName") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            Topics(
                axonViewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToQAndA = { topicId, topicName ->
                    navController.navigate(Screens.QUESTION_AND_ANSWER.name +"/${topicId}/${topicName}")
                },
                categoryId = navBackStackEntry.arguments?.getString("categoryName") ?: ""
            )
        }

        composable (
            route = Screens.QUESTION_AND_ANSWER.name + "/{topicId}/{topicName}",
            arguments = listOf(
                navArgument("topicId") {
                    type = NavType.IntType
                },
                navArgument("topicName"){
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            QuestionAndAnswer(
                axonViewModel = viewModel,
                topicId = navBackStackEntry.arguments?.getInt("topicId") ?: 0,
                topicName = navBackStackEntry.arguments?.getString("topicName") ?: "",
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}