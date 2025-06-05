package com.example.exemplosimplesdecompose.view

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.data.GasStation
import androidx.core.net.toUri
import kotlinx.coroutines.launch
import com.example.exemplosimplesdecompose.R

@Composable
fun Posto(navController: NavHostController, nome: String) {
    val context = LocalContext.current
    val posto = getListOfGasStation(context).find { it.name == nome }

    var alcool by remember { mutableStateOf(posto?.alcohol?.toString() ?: "") }
    var gasolina by remember { mutableStateOf(posto?.gasoline?.toString() ?: "") }
    var nomePosto by remember { mutableStateOf(posto?.name ?: "") }

    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    if (posto == null) {
        Text(stringResource(id = R.string.posto_nao_encontrado))
        return
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = nomePosto,
                onValueChange = { nomePosto = it },
                label = { Text(stringResource(id = R.string.nome_posto)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = alcool,
                onValueChange = { alcool = it },
                label = { Text(stringResource(id = R.string.preco_alcool)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = gasolina,
                onValueChange = { gasolina = it },
                label = { Text(stringResource(id = R.string.preco_gasolina)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(onClick = {
                    val uri = "geo:${posto.coord.lat},${posto.coord.lgt}?q=${posto.coord.lat},${posto.coord.lgt}(${posto.name})"
                    val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
                    intent.setPackage("com.google.android.apps.maps")
                    context.startActivity(intent)
                }) {
                    Text(stringResource(id = R.string.ver_no_mapa))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
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
                }) {
                    Text(stringResource(id = R.string.salvar))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    showDialog = true
                }) {
                    Text(stringResource(id = R.string.excluir))
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(stringResource(id = R.string.confirmacao)) },
                    text = { Text(stringResource(id = R.string.deseja_excluir)) },
                    confirmButton = {
                        Button(onClick = {
                            deleteGasStation(context, posto)
                            showDialog = false

                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.posto_excluido_sucesso)
                                )
                                navController.popBackStack()
                            }
                        }) {
                            Text(stringResource(id = R.string.sim))
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            showDialog = false
                        }) {
                            Text(stringResource(id = R.string.cancelar))
                        }
                    }
                )
            }
        }
    }
}