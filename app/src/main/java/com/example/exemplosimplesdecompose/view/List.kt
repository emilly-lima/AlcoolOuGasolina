package com.example.exemplosimplesdecompose.view

import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.R
import com.example.exemplosimplesdecompose.data.Coordinates
import com.example.exemplosimplesdecompose.data.GasStation
import com.example.exemplosimplesdecompose.ui.theme.*
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListofGasStations(navController: NavHostController) {
    val context = LocalContext.current
    val postosComp = getListOfGasStation(context)
    val currentContrastLevel = loadContrastConfig(context)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = getAppBackground(contrastLevel = currentContrastLevel)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.lista_de_postos),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = getAppOnPrimaryContainer(contrastLevel = currentContrastLevel)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = getAppPrimaryContainer(contrastLevel = currentContrastLevel),
                        titleContentColor = getAppOnPrimaryContainer(contrastLevel = currentContrastLevel)
                    )
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(postosComp) { item ->
                    val descricaoAcessivel = "${item.name}, gasolina R$ %.2f, álcool R$ %.2f, data: %s".format(
                        item.gasoline, item.alcohol, item.date
                    )

                    Card(
                        onClick = {
                            navController.navigate("Posto/${item.name}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .semantics {
                                contentDescription = descricaoAcessivel
                            },
                        colors = androidx.compose.material3.CardDefaults.cardColors(
                            containerColor = getAppSurfaceContainer(contrastLevel = currentContrastLevel),
                            contentColor = getAppOnSurface(contrastLevel = currentContrastLevel)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "${stringResource(id = R.string.nome)}: ${item.name}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = getAppOnSurface(contrastLevel = currentContrastLevel)
                            )
                            Text(
                                text = "${stringResource(id = R.string.gasolina)}: R$ %.2f".format(item.gasoline),
                                style = MaterialTheme.typography.bodyMedium,
                                color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)
                            )
                            Text(
                                text = "${stringResource(id = R.string.alcool)}: R$ %.2f".format(item.alcohol),
                                style = MaterialTheme.typography.bodyMedium,
                                color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)
                            )
                            Text(
                                text = "${stringResource(id = R.string.data)}: ${item.date}",
                                style = MaterialTheme.typography.bodySmall,
                                color = getAppOnSurfaceVariant(contrastLevel = currentContrastLevel)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun gasStationToJson(gasStation: GasStation): JSONObject {
    return JSONObject().apply {
        put("name", gasStation.name)
        put("alcohol", gasStation.alcohol)
        put("gasoline", gasStation.gasoline)
        put("date", gasStation.date)
        put("lat", gasStation.coord.lat)
        put("lgt", gasStation.coord.lgt)
    }
}

fun jsonToGasStation(json: JSONObject?): GasStation {
    val name = json?.optString("name", "") ?: ""
    val alcohol = json?.optDouble("alcohol", 0.0) ?: 0.0
    val gasoline = json?.optDouble("gasoline", 0.0) ?: 0.0
    val date = json?.optString("date", "") ?: ""
    val lat = json?.optDouble("lat", 0.0) ?: 0.0
    val lgt = json?.optDouble("lgt", 0.0) ?: 0.0
    return GasStation(name, alcohol, gasoline, date, Coordinates(lat, lgt))
}

fun saveGasStationJSON(context: Context, gasStation: GasStation) {
    val sharedFileName = "allGasStationsJSON"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    sp.edit {
        val existingList = getListOfGasStation(context).toMutableList()
        existingList.add(gasStation)

        val jsonArray = JSONArray()
        for (gs in existingList) {
            jsonArray.put(gasStationToJson(gs))
        }

        putString("gasStations", jsonArray.toString())
    }
}

fun getListOfGasStation(context: Context): List<GasStation> {
    val sharedFileName = "allGasStationsJSON"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val jsonString = sp.getString("gasStations", null) ?: return emptyList()

    val jsonArray = try {
        JSONArray(jsonString)
    } catch (_: Exception) {
        return emptyList()
    }

    val list = mutableListOf<GasStation>()
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        list.add(jsonToGasStation(jsonObject))
    }
    return list
}

fun updateList(context: Context, lista: List<GasStation>) {
    val sharedFileName = "allGasStationsJSON"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val jsonArray = JSONArray()
    lista.forEach { jsonArray.put(gasStationToJson(it)) }
    sp.edit {
        putString("gasStations", jsonArray.toString())
    }
}

fun deleteGasStation(context: Context, gasStation: GasStation) {
    val currentList = getListOfGasStation(context).toMutableStateList()
    currentList.removeAll { it.name == gasStation.name && it.date == gasStation.date }
    updateList(context, currentList)
}
