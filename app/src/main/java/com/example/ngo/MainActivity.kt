package com.example.ngo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ngo.screens.RegisterEventScreen
import com.example.ngo.screens.RegisterNGOScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ngoNavigation()
        }
    }
}

@Composable
fun ngoNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "register_ngo"){
        composable(route = "register_ngo"){
            RegisterNGOScreen(navController)
        }

        composable(route = "register_event"){
            RegisterEventScreen(navController)
        }
    }

}


