package com.example.dessertclicker.ui

import com.example.dessertclicker.model.Dessert

data class DesertUiState(
    val currentDessert: Dessert,
    val revenue : Int = 0,
    val dessertSold : Int = 0,
    val currentDessertIndex : Int = 0,
    )