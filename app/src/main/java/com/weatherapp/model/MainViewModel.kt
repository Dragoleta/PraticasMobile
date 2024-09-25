package com.weatherapp.model

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.weatherapp.db.repo.Repository

class MainViewModel : ViewModel(), Repository.Listener {

    private val _user = mutableStateOf (User("", ""))

    private val _cities = mutableStateMapOf<String, City>()

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

    val cities : List<City> get() = _cities.values.toList()

    override fun onUserLoaded(user: User) { _user.value = user }
    override fun onCityAdded(city: City) { _cities[city.name] = city }
    override fun onCityRemoved(city: City) { _cities.remove(city.name) }
}
