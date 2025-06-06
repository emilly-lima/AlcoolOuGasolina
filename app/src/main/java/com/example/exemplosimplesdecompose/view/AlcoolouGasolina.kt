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
import androidx.compose.ui.res.stringResource
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
import com.example.exemplosimplesdecompose.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


@Composable
fun AlcoolGasolinaPreco(navController: NavHostController, check: Boolean) {
    val context = LocalContext.current

    var alcool by remember { mutableStateOf("") }
    var gasolina by remember { mutableStateOf("") }
    var nomeDoPosto by remember { mutableStateOf("") }
    var checkedState by remember { mutableStateOf(check) }

    var showRationale by remember { mutableStateOf(false) }
    var permissionDeniedOnce by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf(context.getString(R.string.vamos_calcular)) }

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
                onGranted()
            }
            else -> {
                if (permissionDeniedOnce) {
                    showRationale = true
                } else {
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = alcool,
                onValueChange = { alcool = it },
                label = { Text(stringResource(id = R.string.preco_alcool)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = gasolina,
                onValueChange = { gasolina = it },
                label = { Text(stringResource(id = R.string.preco_gasolina)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = nomeDoPosto,
                onValueChange = { nomeDoPosto = it },
                label = { Text(stringResource(id = R.string.nome_posto_opcional)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(id = R.string.percentual_75),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Switch(
                    modifier = Modifier.semantics { contentDescription = "Escolha o percentual" },
                    checked = checkedState,
                    onCheckedChange = {
                        checkedState = it
                        saveConfig(context, checkedState)
                    },
                    thumbContent = {
                        if (checkedState) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    }
                )
            }

            Button(
                onClick = {
                    val alcoolValor = alcool.toFloatOrNull()
                    val gasolinaValor = gasolina.toFloatOrNull()
                    val fator = if (checkedState) 0.75 else 0.70

                    resultado = if (alcoolValor == null || gasolinaValor == null || gasolinaValor == 0f) {
                        context.getString(R.string.valores_invalidos)
                    } else {
                        if (alcoolValor / gasolinaValor <= fator) {
                            context.getString(R.string.melhor_usar_alcool)
                        } else {
                            context.getString(R.string.melhor_usar_gasolina)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.calcular))
            }

            Text(
                text = resultado,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = {
                        if (alcool.isBlank()) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.informar_preco_alcool),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@FloatingActionButton
                        }
                        if (gasolina.isBlank()) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.informar_preco_gasolina),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@FloatingActionButton
                        }
                        checkAndRequestPermission {
                            getLastKnownLocation(context) { location ->
                                if (location != null) {
                                    val postoDesconhecido = context.getString(R.string.posto_desconhecido)
                                    val nomeFinal = nomeDoPosto.ifEmpty { postoDesconhecido }
                                    val gasStation = GasStation(
                                        name = nomeFinal,
                                        alcohol = alcool.toDoubleOrNull() ?: 0.0,
                                        gasoline = gasolina.toDoubleOrNull() ?: 0.0,
                                        date = SimpleDateFormat(
                                            "dd-MM-yyyy HH:mm",
                                            Locale.getDefault()
                                        ).format(Date()),
                                        coord = Coordinates(location.latitude, location.longitude)
                                    )
                                    saveGasStationJSON(context, gasStation)
                                    navController.navigate("ListaDePostos/${gasStation.name}")
                                }
                            }
                        }
                    }
                ) {
                    Icon(Icons.Filled.Add, context.getString(R.string.inserir_posto))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("ListaDePostos/${nomeDoPosto}") },
                ) {
                    Icon(Icons.Filled.List, context.getString(R.string.ver_lista_posto))
                }
            }
        }
    }

    if (showRationale) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text(stringResource(id = R.string.permissao_necessaria)) },
            text = { Text(stringResource(id = R.string.permissao_texto)) },
            confirmButton = {
                Button(onClick = {
                    showRationale = false
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }) {
                    Text(stringResource(id = R.string.permissao_permitir))
                }
            },
            dismissButton = {
                Button(onClick = { showRationale = false }) {
                    Text(stringResource(id = R.string.permissao_cancelar))
                }
            }
        )
    }
}

fun saveConfig(context: Context, switch_state: Boolean) {
    val sharedFileName = "config_Alc_ou_Gas"
    val sp: SharedPreferences =
        context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    sp.edit {
        putBoolean("is_75_checked", switch_state)
    }
}
