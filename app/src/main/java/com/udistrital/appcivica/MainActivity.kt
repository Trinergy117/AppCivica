package com.udistrital.appcivica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.udistrital.appcivica.data.local.AppDatabase
import com.udistrital.appcivica.data.repository.UserRepositoryImpl
import com.udistrital.appcivica.domain.model.User
import com.udistrital.appcivica.ui.LoginScreen
import com.udistrital.appcivica.ui.MenuScreen
import com.udistrital.appcivica.ui.RegisterScreen
import com.udistrital.appcivica.ui.theme.AppCivicaTheme
import kotlinx.coroutines.launch

enum class Screen {
    LOGIN,
    REGISTER,
    MENU
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getInstance(this)
        val userRepository = UserRepositoryImpl(database.userDao())

        setContent {
            AppCivicaTheme {
                var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
                var currentUser by remember { mutableStateOf<User?>(null) }
                val scope = rememberCoroutineScope()

                when (currentScreen) {
                    Screen.LOGIN -> {
                        LoginScreen(
                            onLoginClick = { email, password, onResult ->
                                scope.launch {
                                    val user = userRepository.authenticate(email, password)
                                    if (user != null) {
                                        currentUser = user
                                        currentScreen = Screen.MENU
                                        onResult(true, null)
                                    } else {
                                        onResult(false, "Credenciales incorrectas")
                                    }
                                }
                            },
                            onRegisterClick = {
                                currentScreen = Screen.REGISTER
                            }
                        )
                    }

                    Screen.REGISTER -> {
                        RegisterScreen(
                            onRegisterClick = { name, email, password, isVulnerable, onResult ->
                                scope.launch {
                                    val existingUser = userRepository.getUserByEmail(email)
                                    if (existingUser != null) {
                                        onResult(false, "El correo electrónico ya se encuentra registrado")
                                    } else {
                                        val newUser = User(
                                            email = email,
                                            name = name,
                                            role = "CIUDADANO",
                                            isVulnerable = isVulnerable
                                        )
                                        userRepository.insertUser(newUser, password)
                                        currentUser = newUser
                                        currentScreen = Screen.MENU
                                        onResult(true, null)
                                    }
                                }
                            },
                            onLoginClick = {
                                currentScreen = Screen.LOGIN
                            }
                        )
                    }

                    Screen.MENU -> {
                        MenuScreen(user = currentUser)
                    }
                }
            }
        }
    }
}
