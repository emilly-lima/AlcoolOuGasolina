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
import com.example.exemplosimplesdecompose.ui.theme.ExemploSimplesDeComposeTheme
import com.example.exemplosimplesdecompose.view.AlcoolGasolinaPreco
import com.example.exemplosimplesdecompose.view.Posto
import com.example.exemplosimplesdecompose.view.ListofGasStations
import com.example.exemplosimplesdecompose.view.Welcome

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var check= false
        check=loadConfig(this)
        setContent {
            ExemploSimplesDeComposeTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { Welcome(navController) }
                   // composable("input") { InputView(navController) }
                    composable("mainalcgas") { AlcoolGasolinaPreco(navController,check) }
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


    fun loadConfig(context: Context):Boolean{
        val sharedFileName="config_Alc_ou_Gas"
        val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, MODE_PRIVATE)
        var is75Checked=false
        if(true) {
            is75Checked = sp.getBoolean("is_75_checked", false)
        }
        return is75Checked
    }
}

