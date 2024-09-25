package com.weatherapp.ui.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.HomePage
import com.weatherapp.ListPage
import com.weatherapp.MainViewModel
import com.weatherapp.MapPage
import com.weatherapp.db.FBDatabase

@Composable
fun MainNavHost(navController: NavHostController, viewModel: MainViewModel,
                modifier: Modifier = Modifier,
                context: Context,
                fbDB: FBDatabase

) {
    NavHost(navController, startDestination = BottomNavItem.HomePage.route) {
        composable(route = BottomNavItem.HomePage.route) {
            HomePage(viewModel = viewModel, modifier = modifier, context = context)
        }
        composable(route = BottomNavItem.ListPage.route) {
            ListPage(viewModel = viewModel, modifier = modifier, context = context, fbDB = fbDB)
        }
        composable(route = BottomNavItem.MapPage.route) {
            MapPage(viewModel = viewModel, modifier = modifier, context = context, fbDB = fbDB)
        }
    }
}