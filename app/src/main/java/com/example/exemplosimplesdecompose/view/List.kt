package com.example.exemplosimplesdecompose.view

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.exemplosimplesdecompose.data.Coordinates
import com.example.exemplosimplesdecompose.data.GasStation
import org.json.JSONArray
import org.json.JSONObject
import androidx.core.content.edit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListofGasStations(navController: NavHostController) {
    val context = LocalContext.current

    //Não precisa salvar sempre que listar
    //val newGas = GasStation(posto)
    //addGasStation(context, newGas)

    // Lista atualizada
    val postosComp = getListOfGasStation(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Postos") }
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
                Card(
                    onClick = {
                        navController.navigate("Posto/${item.name}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Nome: ${item.name}")
                        Text("Gasolina: R$ %.2f".format(item.gasoline))
                        Text("Álcool: R$ %.2f".format(item.alcohol))
                        Text("Data: ${item.date}")
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

//fun stringToJson_Safe(jsonString: String): JSONObject? {
//    return try {
//        JSONObject(jsonString)
//    } catch (e: Exception) {
//        null
//    }
//}

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
    } catch (e: Exception) {
        return emptyList()
    }

    val list = mutableListOf<GasStation>()
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        list.add(jsonToGasStation(jsonObject))
    }
    return list
}

//fun addGasStation(context: Context, gasStation: GasStation) {
//    saveGasStationJSON(context, gasStation)
//}

fun updateList(context: Context, lista: List<GasStation>) {
    val sharedFileName = "allGasStationsJSON"
    val sp = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    val jsonArray = JSONArray()
    lista.forEach { jsonArray.put(gasStationToJson(it)) }
    sp.edit {
        putString("gasStations", jsonArray.toString())
    }
}

fun deleteGasStation(context: Context, gasStation: GasStation){
    val currentList = getListOfGasStation(context).toMutableStateList()
    currentList.removeAll { it.name == gasStation.name && it.date == gasStation.date }
    updateList(context, currentList)
}

