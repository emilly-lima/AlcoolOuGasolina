package com.example.exemplosimplesdecompose.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.exemplosimplesdecompose.data.GasStation
import androidx.core.net.toUri

@Composable
fun Posto(navController: NavHostController, nome: String) {
    val context = LocalContext.current
    val posto = getListOfGasStation(context).find { it.name == nome }

    var alcool by remember { mutableStateOf(posto?.alcohol?.toString() ?: "") }
    var gasolina by remember { mutableStateOf(posto?.gasoline?.toString() ?: "") }
    var nomePosto by remember { mutableStateOf(posto?.name ?: "") }

    if (posto == null) {
        Text("Posto não encontrado")
        return
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.padding(16.dp)) {
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
                    deleteGasStation(context, posto)
                    navController.popBackStack()
                }) {
                    Text("Excluir")
                }
            }
        }
    }
}