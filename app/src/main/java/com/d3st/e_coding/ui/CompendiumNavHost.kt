package com.d3st.e_coding.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.d3st.e_coding.ui.compendium.CompendiumScreen
import com.d3st.e_coding.ui.compendium.CompendiumViewModel
import com.d3st.e_coding.ui.details.DetailsScreen

@Composable
fun CompendiumNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: CompendiumViewModel = viewModel(),
) {
    NavHost(navController = navController, startDestination = AppScreens.COMPENDIUM.value) {
        composable(AppScreens.COMPENDIUM.value) {
            CompendiumScreen(
                viewModel = viewModel,
                onAdditiveClick = { input -> navController.navigate("${AppScreens.DETAILS.value}/$input") })
            //onAdditiveClick = { })
        }
        composable("${AppScreens.DETAILS.value}/{name}") { args ->
            args.arguments?.getString("name")?.let {
                DetailsScreen(
                    additive = viewModel.getByName(it)
                )
            }
        }
    }

}