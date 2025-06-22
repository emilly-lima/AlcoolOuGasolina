package com.example.exemplosimplesdecompose.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.R
import com.example.exemplosimplesdecompose.data.Coordinates
import com.example.exemplosimplesdecompose.data.GasStation
import com.example.exemplosimplesdecompose.ui.theme.ContrastLevel
import com.example.exemplosimplesdecompose.ui.theme.getAppBackground
import com.example.exemplosimplesdecompose.ui.theme.getAppError
import com.example.exemplosimplesdecompose.ui.theme.getAppOnPrimary
import com.example.exemplosimplesdecompose.ui.theme.getAppOnPrimaryContainer
import com.example.exemplosimplesdecompose.ui.theme.getAppOnSecondary
import com.example.exemplosimplesdecompose.ui.theme.getAppOnSecondaryContainer
import com.example.exemplosimplesdecompose.ui.theme.getAppOnSurface
import com.example.exemplosimplesdecompose.ui.theme.getAppOnSurfaceVariant
import com.example.exemplosimplesdecompose.ui.theme.getAppOutline
import com.example.exemplosimplesdecompose.ui.theme.getAppPrimary
import com.example.exemplosimplesdecompose.ui.theme.getAppPrimaryContainer
import com.example.exemplosimplesdecompose.ui.theme.getAppSecondary
import com.example.exemplosimplesdecompose.ui.theme.getAppSecondaryContainer
import com.example.exemplosimplesdecompose.ui.theme.getAppSurface
import com.example.exemplosimplesdecompose.ui.theme.getAppSurfaceVariant
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun AlcoolGasolinaPreco(navController: NavHostController, check: Boolean) {
    val context = LocalContext.current
    val currentContrastLevel = loadContrastConfig(context)

    var alcool by remember { mutableStateOf("") }
    var gasolina by remember { mutableStateOf("") }
    var nomeDoPosto by remember { mutableStateOf("") }
    var checkedState by remember { mutableStateOf(check) }

    var showRationale by remember { mutableStateOf(false) }
    var permissionDeniedOnce by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf(context.getString(R.string.vamos_calcular)) }

    // Estados para configurações de tema
    var showThemeDialog by remember { mutableStateOf(false) }

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
        color = getAppBackground(contrastLevel = currentContrastLevel)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                color = getAppPrimary(contrastLevel = currentContrastLevel),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = alcool,
                onValueChange = { alcool = it },
                label = { Text(stringResource(id = R.string.preco_alcool), color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getAppPrimary(contrastLevel = currentContrastLevel),
                    unfocusedBorderColor = getAppOutline(contrastLevel = currentContrastLevel),
                    focusedLabelColor = getAppPrimary(contrastLevel = currentContrastLevel),
                    unfocusedLabelColor = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel),
                    cursorColor = getAppPrimary(contrastLevel = currentContrastLevel)
                )
            )
            OutlinedTextField(
                value = gasolina,
                onValueChange = { gasolina = it },
                label = { Text(stringResource(id = R.string.preco_gasolina), color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getAppPrimary(contrastLevel = currentContrastLevel),
                    unfocusedBorderColor = getAppOutline(contrastLevel = currentContrastLevel),
                    focusedLabelColor = getAppPrimary(contrastLevel = currentContrastLevel),
                    unfocusedLabelColor = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel),
                    cursorColor = getAppPrimary(contrastLevel = currentContrastLevel)
                )
            )
            OutlinedTextField(
                value = nomeDoPosto,
                onValueChange = { nomeDoPosto = it },
                label = { Text(stringResource(id = R.string.nome_posto_opcional), color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getAppPrimary(contrastLevel = currentContrastLevel),
                    unfocusedBorderColor = getAppOutline(contrastLevel = currentContrastLevel),
                    focusedLabelColor = getAppPrimary(contrastLevel = currentContrastLevel),
                    unfocusedLabelColor = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel),
                    cursorColor = getAppPrimary(contrastLevel = currentContrastLevel)
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.percentual_75),
                    style = MaterialTheme.typography.bodyLarge,
                    color = getAppOnSurface(contrastLevel = currentContrastLevel)
                )
                Switch(
                    modifier = Modifier.semantics { contentDescription = "Escolha o percentual" },
                    checked = checkedState,
                    onCheckedChange = {
                        checkedState = it
                        saveConfig(context, checkedState)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = getAppOnPrimary(contrastLevel = currentContrastLevel),
                        checkedTrackColor = getAppPrimary(contrastLevel = currentContrastLevel),
                        uncheckedThumbColor = getAppOutline(contrastLevel = currentContrastLevel),
                        uncheckedTrackColor = getAppSurfaceVariant(contrastLevel = currentContrastLevel)
                    ),
                    thumbContent = {
                        if (checkedState) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                tint = getAppPrimary(contrastLevel = currentContrastLevel)
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
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = getAppPrimary(contrastLevel = currentContrastLevel),
                    contentColor = getAppOnPrimary(contrastLevel = currentContrastLevel)
                )
            ) {
                Text(stringResource(id = R.string.calcular))
            }

            Text(
                text = resultado,
                style = MaterialTheme.typography.bodyLarge,
                color = if (resultado.contains("inválidos")) getAppError(contrastLevel = currentContrastLevel) else getAppOnSurface(contrastLevel = currentContrastLevel),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Botão de configurações de tema
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Button(
                    onClick = { showThemeDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getAppSecondary(contrastLevel = currentContrastLevel),
                        contentColor = getAppOnSecondary(contrastLevel = currentContrastLevel)
                    )
                ) {
                    Text("Configurações de Tema")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
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
                    },
                    containerColor = getAppPrimaryContainer(contrastLevel = currentContrastLevel),
                    contentColor = getAppOnPrimaryContainer(contrastLevel = currentContrastLevel)
                ) {
                    Icon(Icons.Filled.Add, context.getString(R.string.inserir_posto))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("ListaDePostos/${nomeDoPosto}") },
                    containerColor = getAppSecondaryContainer(contrastLevel = currentContrastLevel),
                    contentColor = getAppOnSecondaryContainer(contrastLevel = currentContrastLevel)
                ) {
                    Icon(Icons.AutoMirrored.Filled.List, context.getString(R.string.ver_lista_posto))
                }
            }
        }
    }

    // Dialog de configurações de tema
    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            containerColor = getAppSurface(contrastLevel = currentContrastLevel),
            title = { 
                Text(
                    text = "Configurações de Tema",
                    style = MaterialTheme.typography.headlineSmall,
                    color = getAppOnSurface(contrastLevel = currentContrastLevel)
                )
            },
            text = { 
                Column {
                    Text(
                        text = "Nível de Contraste:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    var currentContrastLevel by remember { mutableStateOf(loadContrastConfig(context)) }
                    var currentDynamicColor by remember { mutableStateOf(loadDynamicColorConfig(context)) }
                    
                    ContrastLevel.entries.forEach { contrastOption ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = currentContrastLevel == contrastOption,
                                onClick = { 
                                    currentContrastLevel = contrastOption
                                    saveContrastConfig(context, contrastOption)
                                },
                                colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                    selectedColor = getAppPrimary(contrastLevel = currentContrastLevel),
                                    unselectedColor = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)
                                )
                            )
                            Text(
                                text = when(contrastOption) {
                                    ContrastLevel.Default -> "Padrão"
                                    ContrastLevel.Medium -> "Médio"
                                    ContrastLevel.High -> "Alto"
                                },
                                color = getAppOnSurface(contrastLevel = currentContrastLevel),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = currentDynamicColor,
                            onCheckedChange = { 
                                currentDynamicColor = it
                                saveDynamicColorConfig(context, it)
                            },
                            colors = androidx.compose.material3.CheckboxDefaults.colors(
                                checkedColor = getAppPrimary(contrastLevel = currentContrastLevel),
                                uncheckedColor = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)
                            )
                        )
                        Text(
                            text = "Cores Dinâmicas (Android 12+)",
                            color = getAppOnSurface(contrastLevel = currentContrastLevel),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { 
                        showThemeDialog = false
                        (context as? ComponentActivity)?.recreate()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getAppPrimary(contrastLevel = currentContrastLevel),
                        contentColor = getAppOnPrimary(contrastLevel = currentContrastLevel)
                    )
                ) {
                    Text("Aplicar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showThemeDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getAppSecondaryContainer(contrastLevel = currentContrastLevel),
                        contentColor = getAppOnSecondaryContainer(contrastLevel = currentContrastLevel)
                    )
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Dialog de solicitação de permissão
    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            containerColor = getAppSurface(contrastLevel = currentContrastLevel),
            title = { 
                Text(
                    text = "Permissão Necessária",
                    color = getAppOnSurface(contrastLevel = currentContrastLevel)
                )
            },
            text = { 
                Text(
                    text = "Este aplicativo precisa da permissão de localização para funcionar corretamente.",
                    color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showRationale = false
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getAppPrimary(contrastLevel = currentContrastLevel),
                        contentColor = getAppOnPrimary(contrastLevel = currentContrastLevel)
                    )
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showRationale = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getAppSecondaryContainer(contrastLevel = currentContrastLevel),
                        contentColor = getAppOnSecondaryContainer(contrastLevel = currentContrastLevel)
                    )
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

fun saveConfig(context: Context, switchState: Boolean) {
    val sharedFileName = "config_Alc_ou_Gas"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    sp.edit {
        putBoolean("is_75_checked", switchState)
    }
}

fun saveContrastConfig(context: Context, contrastLevel: ContrastLevel) {
    val sharedFileName = "config_Alc_ou_Gas"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    sp.edit {
        putInt("contrast_level", contrastLevel.ordinal)
    }
}

fun loadContrastConfig(context: Context): ContrastLevel {
    val sharedFileName = "config_Alc_ou_Gas"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val contrastIndex = sp.getInt("contrast_level", 0)
    return ContrastLevel.entries[contrastIndex]
}

fun saveDynamicColorConfig(context: Context, dynamicColor: Boolean) {
    val sharedFileName = "config_Alc_ou_Gas"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    sp.edit {
        putBoolean("dynamic_color", dynamicColor)
    }
}

fun loadDynamicColorConfig(context: Context): Boolean {
    val sharedFileName = "config_Alc_ou_Gas"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    return sp.getBoolean("dynamic_color", true)
}
