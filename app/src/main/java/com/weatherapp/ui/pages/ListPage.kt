package com.weatherapp.ui.pages

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.weatherapp.R
import com.weatherapp.model.MainViewModel
import com.weatherapp.repo.Repository
import com.weatherapp.model.City
import com.weatherapp.ui.nav.BottomNavItem


@Composable
fun CityItem(
    city: City,
    onClick: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = if (city.isMonitored) Icons.Outlined.Favorite else  Icons.Outlined.FavoriteBorder

    Row(
        modifier = modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = city.weather?.imgUrl,
            modifier = Modifier.size(75.dp),
            error = painterResource(id = R.drawable.loading_icon_with_fade),
            contentDescription = "Image"
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = modifier.weight(1f)) {
            Text(modifier = Modifier,
                text = city.name,
                fontSize = 24.sp)
            Text(modifier = Modifier,
                text = city.weather?.desc?: "Loading...",
                fontSize = 16.sp)
        }
        Icon(
            imageVector = icon,
            contentDescription = "Monitor?",
            modifier = Modifier.size(32.dp)
        )
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@Composable
fun ListPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    context: Context,
    repo: Repository,
    navController: NavHostController
) {
    val cityList = viewModel.cities

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(cityList) { city ->
            if (city.weather == null) {
                repo.loadWeather(city)
            }
            CityItem(city = city, onClose = {
                repo.remove(city)
            },
            onClick = {
                viewModel.city = city
                repo.loadForecast(city)
                navController.navigate(BottomNavItem.HomePage.route) {
                    navController.graph.startDestinationRoute?.let {
                        popUpTo(it) { saveState = true }
                        restoreState = true
                    }
                    launchSingleTop = true
                }
            })
        }
    }
}
