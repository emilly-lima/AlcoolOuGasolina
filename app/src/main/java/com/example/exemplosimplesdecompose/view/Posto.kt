package com.example.exemplosimplesdecompose.view

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.R
import com.example.exemplosimplesdecompose.data.GasStation
import com.example.exemplosimplesdecompose.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun Posto(navController: NavHostController, nome: String) {
    val context = LocalContext.current
    val posto = getListOfGasStation(context).find { it.name == nome }
    val currentContrastLevel = loadContrastConfig(context)

    var alcool by remember { mutableStateOf(posto?.alcohol?.toString() ?: "") }
    var gasolina by remember { mutableStateOf(posto?.gasoline?.toString() ?: "") }
    var nomePosto by remember { mutableStateOf(posto?.name ?: "") }

    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    if (posto == null) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = getAppBackground(contrastLevel = currentContrastLevel)
        ) {
            Text(
                text = stringResource(id = R.string.posto_nao_encontrado),
                style = MaterialTheme.typography.bodyLarge,
                color = getAppError(contrastLevel = currentContrastLevel),
                modifier = Modifier.padding(16.dp)
            )
        }
        return
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
            color = getAppBackground(contrastLevel = currentContrastLevel)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.nome_posto),
                    style = MaterialTheme.typography.headlineSmall,
                    color = getAppPrimary(contrastLevel = currentContrastLevel),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = nomePosto,
                    onValueChange = { nomePosto = it },
                    label = { Text(stringResource(id = R.string.nome_posto), color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = getAppPrimary(contrastLevel = currentContrastLevel),
                        unfocusedBorderColor = getAppOutline(contrastLevel = currentContrastLevel),
                        focusedLabelColor = getAppPrimary(contrastLevel = currentContrastLevel),
                        unfocusedLabelColor = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel),
                        cursorColor = getAppPrimary(contrastLevel = currentContrastLevel)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = alcool,
                    onValueChange = { alcool = it },
                    label = { Text(stringResource(id = R.string.preco_alcool), color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = getAppPrimary(contrastLevel = currentContrastLevel),
                        unfocusedBorderColor = getAppOutline(contrastLevel = currentContrastLevel),
                        focusedLabelColor = getAppPrimary(contrastLevel = currentContrastLevel),
                        unfocusedLabelColor = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel),
                        cursorColor = getAppPrimary(contrastLevel = currentContrastLevel)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = gasolina,
                    onValueChange = { gasolina = it },
                    label = { Text(stringResource(id = R.string.preco_gasolina), color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = getAppPrimary(contrastLevel = currentContrastLevel),
                        unfocusedBorderColor = getAppOutline(contrastLevel = currentContrastLevel),
                        focusedLabelColor = getAppPrimary(contrastLevel = currentContrastLevel),
                        unfocusedLabelColor = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel),
                        cursorColor = getAppPrimary(contrastLevel = currentContrastLevel)
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    Button(
                        onClick = {
                            val uri = "geo:${posto.coord.lat},${posto.coord.lgt}?q=${posto.coord.lat},${posto.coord.lgt}(${posto.name})"
                            val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
                            intent.setPackage("com.google.android.apps.maps")
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getAppSecondary(contrastLevel = currentContrastLevel),
                            contentColor = getAppOnSecondary(contrastLevel = currentContrastLevel)
                        )
                    ) {
                        Text(stringResource(id = R.string.ver_no_mapa))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val novaLista = getListOfGasStation(context).filter { it.name != posto.name }.toMutableList()
                            novaLista.add(
                                GasStation(
                                    name = nomePosto,
                                    alcohol = alcool.toDoubleOrNull() ?: 0.0,
                                    gasoline = gasolina.toDoubleOrNull() ?: 0.0,
                                    date = posto.date,
                                    coord = posto.coord
                                )
                            )
                            updateList(context, novaLista)
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getAppPrimary(contrastLevel = currentContrastLevel),
                            contentColor = getAppOnPrimary(contrastLevel = currentContrastLevel)
                        )
                    ) {
                        Text(stringResource(id = R.string.salvar))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            showDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = getAppErrorContainer(contrastLevel = currentContrastLevel),
                            contentColor = getAppOnErrorContainer(contrastLevel = currentContrastLevel)
                        )
                    ) {
                        Text(stringResource(id = R.string.excluir))
                    }
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        containerColor = getAppSurface(contrastLevel = currentContrastLevel),
                        title = { 
                            Text(
                                text = stringResource(id = R.string.confirmacao),
                                style = MaterialTheme.typography.headlineSmall,
                                color = getAppOnSurface(contrastLevel = currentContrastLevel)
                            )
                        },
                        text = { 
                            Text(
                                text = stringResource(id = R.string.deseja_excluir),
                                style = MaterialTheme.typography.bodyMedium,
                                color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    deleteGasStation(context, posto)
                                    showDialog = false

                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = context.getString(R.string.posto_excluido_sucesso)
                                        )
                                        navController.popBackStack()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = getAppError(contrastLevel = currentContrastLevel),
                                    contentColor = getAppOnError(contrastLevel = currentContrastLevel)
                                )
                            ) {
                                Text(stringResource(id = R.string.sim))
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = getAppSecondaryContainer(contrastLevel = currentContrastLevel),
                                    contentColor = getAppOnSecondaryContainer(contrastLevel = currentContrastLevel)
                                )
                            ) {
                                Text(stringResource(id = R.string.cancelar))
                            }
                        }
                    )
                }
            }
        }
    }
}