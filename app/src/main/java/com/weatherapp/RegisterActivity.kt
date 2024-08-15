package com.weatherapp

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.weatherapp.db.FBDatabase
import com.weatherapp.ui.theme.WeatherAppTheme


class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterPage()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RegisterPage(modifier: Modifier = Modifier) {

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var conPassword by rememberSaveable { mutableStateOf("") }

    val activity = LocalContext.current as? Activity
    val fbDB = remember { FBDatabase() }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier.padding(16.dp),
    ) {
        Text(
            text = "Bem-vindo/a!",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.size(24.dp))
        OutlinedTextField(

            value = email,
            label = { Text(text = "Digite seu e-mail") },
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { email = it }
        )
        Box {
            OutlinedTextField(
                value = name,
                label = { Text(text = "Digite seu nome") },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { name = it },
            )
        }
        Box {
            OutlinedTextField(
                value = password,
                label = { Text(text = "Digite sua senha") },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Box {
            OutlinedTextField(
                value = conPassword,
                label = { Text(text = "Confirme sua senha") },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { conPassword = it },
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Spacer(modifier = Modifier.size(24.dp))
        Row(modifier = modifier) {

            Button(
                enabled = email.isNotEmpty()&& name.isNotEmpty() && password.isNotEmpty()&& conPassword.isNotEmpty(),

                onClick = {
                    Firebase.auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(activity,
                                    "Registro OK!", Toast.LENGTH_LONG).show()
                                activity.finish()
                            } else {
                                Toast.makeText(activity,
                                    "Registro FALHOU!", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            ) {
                Text(text = "Register")
            }
            Spacer(modifier = Modifier.size(24.dp))

            Button(
                onClick = { email = ""; password = ""; conPassword=""; name = ""; }
            ) {

                Text(text = "Limpar")


            }
        }
    }
}

