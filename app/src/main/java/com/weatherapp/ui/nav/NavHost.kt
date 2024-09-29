package com.weatherapp.ui.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.ui.pages.HomePage
import com.weatherapp.ui.pages.ListPage
import com.weatherapp.model.MainViewModel
import com.weatherapp.ui.pages.MapPage
import com.weatherapp.repo.Repository

@Composable
fun MainNavHost(navController: NavHostController, viewModel: MainViewModel,
                modifier: Modifier = Modifier,
                context: Context,
                repo: Repository

) {
    NavHost(navController, startDestination = BottomNavItem.HomePage.route) {
        composable(route = BottomNavItem.HomePage.route) {
            HomePage(viewModel = viewModel, modifier = modifier, context = context)
        }
        composable(route = BottomNavItem.ListPage.route) {
            ListPage(viewModel = viewModel, modifier = modifier, context = context,  repo = repo, navController = navController)
        }
        composable(route = BottomNavItem.MapPage.route) {
            MapPage(viewModel = viewModel, modifier = modifier, context = context,  repo = repo)
        }
    }
}