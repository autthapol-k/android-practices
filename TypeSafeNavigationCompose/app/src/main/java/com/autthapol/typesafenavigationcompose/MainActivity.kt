package com.autthapol.typesafenavigationcompose

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.autthapol.typesafenavigationcompose.ui.theme.TypeSafeNavigationComposeTheme
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TypeSafeNavigationComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ScreenA
                ) {
                    composable<ScreenA>
                    {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Button(onClick = {
                                navController.navigate(
                                    ScreenB(
                                        name = "John",
                                        age = 21,
                                    )
                                )
                            }) {
                                Text(text = "Go to Screen B")
                            }
                        }
                    }
                    composable<ScreenB>(
                        typeMap = mapOf(typeOf<User>() to UserType)
                    ) {
                        val args = it.toRoute<ScreenB>()
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = "${args.name} ${args.age} years old")
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object ScreenA

@Serializable
data class ScreenB(
    val name: String,
    val age: Int,
    // still not working
    // val user: User,
)


@Parcelize
@Serializable
data class User(
    val name: String,
    val age: Int
) : Parcelable

val UserType = object : NavType<User>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): User? {
        return bundle.getParcelable(key, User::class.java)
    }

    override fun parseValue(value: String): User {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: User) {
        bundle.putParcelable(key, value)
    }
}