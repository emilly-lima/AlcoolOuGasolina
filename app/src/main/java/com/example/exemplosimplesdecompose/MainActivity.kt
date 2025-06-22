package com.example.exemplosimplesdecompose

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exemplosimplesdecompose.ui.theme.AppTheme
import com.example.exemplosimplesdecompose.ui.theme.ContrastLevel
import com.example.exemplosimplesdecompose.view.AlcoolGasolinaPreco
import com.example.exemplosimplesdecompose.view.ListofGasStations
import com.example.exemplosimplesdecompose.view.Posto
import com.example.exemplosimplesdecompose.view.Welcome

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val check = loadConfig(this@MainActivity)
            val contrastLevel = loadContrastConfig(this@MainActivity)
            val dynamicColor = loadDynamicColorConfig(this@MainActivity)

            AppTheme(
                contrastLevel = contrastLevel,
                dynamicColor = dynamicColor
            ) {
                val navController: NavHostController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { Welcome(navController) }
                    composable("mainalcgas") { AlcoolGasolinaPreco(navController, check) }
                    composable("listaDePostos/{posto}") { backStackEntry ->
                        backStackEntry.arguments?.getString("posto") ?: ""
                        ListofGasStations(navController)
                    }
                    composable("Posto/{nome}") { backStackEntry ->
                        val nome = backStackEntry.arguments?.getString("nome") ?: ""
                        Posto(navController, nome)
                    }
                }
            }
        }
    }

    private fun loadConfig(context: Context): Boolean {
        val sharedFileName = "config_Alc_ou_Gas"
        val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, MODE_PRIVATE)
        return sp.getBoolean("is_75_checked", false)
    }

    private fun loadContrastConfig(context: Context): ContrastLevel {
        val sharedFileName = "config_Alc_ou_Gas"
        val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, MODE_PRIVATE)
        val contrastIndex = sp.getInt("contrast_level", 0)
        return ContrastLevel.entries[contrastIndex]
    }

    private fun loadDynamicColorConfig(context: Context): Boolean {
        val sharedFileName = "config_Alc_ou_Gas"
        val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, MODE_PRIVATE)
        return sp.getBoolean("dynamic_color", true)
    }
}

