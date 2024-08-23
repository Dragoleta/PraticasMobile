package com.weatherapp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.weatherapp.db.FBDatabase
import com.weatherapp.model.City
import com.weatherapp.model.User

class MainViewModel : ViewModel(),  FBDatabase.Listener {

    private val _user = mutableStateOf (User("", ""))


    private val _cities = mutableStateListof<City>()

    val user : User
        get() = _user.value

    private var _loggedIn = mutableStateOf(false)

    val loggedIn : Boolean
        get() = _loggedIn.value

    private val listener = FirebaseAuth.AuthStateListener {
            firebaseAuth -> _loggedIn.value = firebaseAuth.currentUser != null
    }

    init {
        listener.onAuthStateChanged(Firebase.auth)
        Firebase.auth.addAuthStateListener(listener)
    }


    val cities : List<City>
        get() = _cities

    override fun onUserLoaded(user: User) {
        _user.value = user
    }
    override fun onCityAdded(city: City) {
        _cities.add(city)
    }
    override fun onCityRemoved(city: City) {
        _cities.remove(city)
    }
}

fun getCities() = List(0) { i ->
    City(name = "Cidade $i", weather = "Carregando clima...")
}
