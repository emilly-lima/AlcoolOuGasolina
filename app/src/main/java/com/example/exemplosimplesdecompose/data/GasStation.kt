package com.example.exemplosimplesdecompose.data

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class GasStation(
    val name: String,
    val alcohol: Double,
    val gasoline: Double,
    val date: String,
    val coord: Coordinates
): Serializable {
    constructor(name: String) : this(
        name = name,
        alcohol = 0.0,
        gasoline = 0.0,
        date = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date()),
        Coordinates(41.40338, 2.17403))
    }