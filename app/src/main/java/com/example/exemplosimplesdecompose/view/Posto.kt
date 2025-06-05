package com.example.exemplosimplesdecompose.view

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.data.GasStation
import androidx.core.net.toUri

import kotlinx.coroutines.launch

@Composable
fun Posto(navController: NavHostController, nome: String) {
    val context = LocalContext.current
    val posto = getListOfGasStation(context).find { it.name == nome }

    var alcool by remember { mutableStateOf(posto?.alcohol?.toString() ?: "") }
    var gasolina by remember { mutableStateOf(posto?.gasoline?.toString() ?: "") }
    var nomePosto by remember { mutableStateOf(posto?.name ?: "") }

    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope() // <- necessário para Snackbar

    if (posto == null) {
        Text("Posto não encontrado")
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
            // TextFields e botões de ação
            OutlinedTextField(
                value = nomePosto,
                onValueChange = { nomePosto = it },
                label = { Text("Nome do Posto") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = alcool,
                onValueChange = { alcool = it },
                label = { Text("Álcool") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = gasolina,
                onValueChange = { gasolina = it },
                label = { Text("Gasolina") },
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
                    Text("Ver no Mapa")
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
                    Text("Salvar")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = {
                    showDialog = true
                }) {
                    Text("Excluir")
                }
            }

            // Dialog de confirmação
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirmação") },
                    text = { Text("Tem certeza que deseja excluir este posto?") },
                    confirmButton = {
                        Button(onClick = {
                            deleteGasStation(context, posto)
                            showDialog = false

                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Posto excluído com sucesso")
                                navController.popBackStack()
                            }
                        }) {
                            Text("Sim")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            showDialog = false
                        }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
