package com.weatherapp.db.repo

import com.google.android.gms.maps.model.LatLng
import com.weatherapp.db.FBDatabase
import com.weatherapp.model.City
import com.weatherapp.model.User

class Repository(private var listener: Listener): FBDatabase.Listener {
    private var fbDb = FBDatabase (this)
    //private var weatherService = WeatherService()

    interface Listener {
        fun onUserLoaded(user: User)
        fun onCityAdded(city: City)
        fun onCityRemoved(city: City)
    }
    fun addCity(name: String) {
        fbDb.add(City(name = name, weather = "", location = LatLng(0.0,0.0)))
    }
    fun addCityLoc(lat: Double, lng: Double) {
        fbDb.add(City(name = "Cidade@$lat:$lng", weather = "", location =  LatLng(lat,lng)))
    }
    fun remove(city: City) {
        fbDb.remove(city)
    }
    fun register(userName: String, email: String) {
        fbDb.register(User(userName, email))
    }
    override fun onUserLoaded(user: User) {
        listener.onUserLoaded(user)
    }
    override fun onCityAdded(city: City) {
        listener.onCityAdded(city)
    }
    override fun onCityRemoved(city: City) {
        listener.onCityRemoved(city)
    }
}