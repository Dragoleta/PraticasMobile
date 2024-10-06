package com.weatherapp

import android.app.Application
import com.weatherapp.db.monitor.ForecastMonitor
import com.weatherapp.db.repo.Repository

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val monitor = ForecastMonitor(this)
        val repo = Repository(context = this, listener = monitor)
    }
}