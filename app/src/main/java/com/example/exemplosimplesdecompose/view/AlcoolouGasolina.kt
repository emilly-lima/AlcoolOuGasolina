package com.example.exemplosimplesdecompose.view

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.data.Coordinates
import com.example.exemplosimplesdecompose.data.GasStation
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.core.content.edit

@Composable
fun AlcoolGasolinaPreco(navController: NavHostController,check:Boolean) {
    val context = LocalContext.current

    var alcool by remember { mutableStateOf("") }
    var gasolina by remember { mutableStateOf("") }
    var nomeDoPosto by remember { mutableStateOf("") }
    var checkedState by remember { mutableStateOf(check) }

    var showRationale by remember { mutableStateOf(false) }
    var permissionDeniedOnce by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            permissionDeniedOnce = true
        }
    }

    fun checkAndRequestPermission(onGranted: () -> Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // Permissão já concedida
                onGranted()
            }
            else -> {
                if (permissionDeniedOnce) {
                    // Permissão negada antes, mostramos explicação
                    showRationale = true
                } else {
                    // Primeira vez pedindo permissão
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }
    }

    fun getLastKnownLocation(context: Context, onLocationReceived: (Location?) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                onLocationReceived(location)
            }
            .addOnFailureListener {
                onLocationReceived(null)
            }
    }

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo de texto para entrada do preço
            OutlinedTextField(
                value = alcool,
                onValueChange = { alcool = it }, // Atualiza o estado
                label = { Text("Preço do Álcool (R$)") },
                modifier = Modifier.fillMaxWidth(), // Preenche a largura disponível
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Configuração do teclado
            )
            // Campo de texto para preço da Gasolina
            OutlinedTextField(
                value = gasolina,
                onValueChange = { gasolina = it },
                label = { Text("Preço da Gasolina (R$)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            // Campo de texto para preço da Gasolina
            OutlinedTextField(
                value = nomeDoPosto,
                onValueChange = { nomeDoPosto = it },
                label = { Text("Nome do Posto (Opcional))") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "75%",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Switch(
                    modifier = Modifier.semantics { contentDescription = "Escolha o percentual" },
                    checked = checkedState,
                    onCheckedChange = { checkedState = it
                         saveConfig(context,checkedState)
                                      },
                    thumbContent = {
                        if (checkedState) {
                            // Icon isn't focusable, no need for content description
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    }
                )
            }
            // Botão de cálculo
            Button(
                onClick = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            // Texto do resultado
            Text(
                text = "Vamos Calcular?",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.End) {
                FloatingActionButton(
                    onClick = {
                        if (alcool.isBlank()) {
                            Toast.makeText(context, "Informe o preço do Álcool.", Toast.LENGTH_SHORT).show()
                            return@FloatingActionButton
                        }
                        if (gasolina.isBlank()) {
                            Toast.makeText(context, "Informe o preço da Gasolina.", Toast.LENGTH_SHORT).show()
                            return@FloatingActionButton
                        }
                        checkAndRequestPermission {
                            // Aqui você chama o getLastKnownLocation e salva o posto
                            getLastKnownLocation(context) { location ->
                                if (location != null) {
                                    val lat = location.latitude
                                    val lng = location.longitude

                                    val gasStation = GasStation(
                                        name = nomeDoPosto.ifEmpty { "Posto Desconhecido" },
                                        alcohol = alcool.toDoubleOrNull() ?: 0.0,
                                        gasoline = gasolina.toDoubleOrNull() ?: 0.0,
                                        date = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(
                                            Date()
                                        ),
                                        coord = Coordinates(lat, lng)
                                    )
                                    saveGasStationJSON(context, gasStation)
                                    navController.navigate("ListaDePostos/${gasStation.name}")
                                }
                            }
                        }
                    }
                ) {
                    Icon(Icons.Filled.Add, "Inserir Posto")
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.End) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("ListaDePostos/$nomeDoPosto")},
                ) {
                    Icon(Icons.Filled.List, "Ver lista de Posto")
                }
            }
        }
    }

    if (showRationale) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text("Permissão necessária") },
            text = {
                Text("O app precisa da sua localização para salvar o posto com coordenadas. Por favor, conceda a permissão.")
            },
            confirmButton = {
                Button(onClick = {
                    showRationale = false
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }) {
                    Text("Permitir")
                }
            },
            dismissButton = {
                Button(onClick = { showRationale = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

}
fun saveConfig(context: Context, switch_state:Boolean){
    val sharedFileName="config_Alc_ou_Gas"
    val sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    sp.edit {
        putBoolean("is_75_checked", switch_state)
    }
}

