package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DesertViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DesertUiState(dessertList.first()))
    val uiState = _uiState.asStateFlow()

    private fun getCurrentDesert() : Dessert{
        return dessertList[uiState.value.currentDessertIndex]
    }

    fun desertSold(revenue : Int, dessertSold : Int){
        _uiState.update { currentState ->
            currentState.copy(
                currentDessert = getCurrentDesert(),
                revenue = currentState.revenue.plus(revenue),
                dessertSold = currentState.dessertSold.plus(dessertSold),
                currentDessertIndex = currentState.currentDessertIndex.inc()
            )
        }
        determineDessertToShow()
    }

    private fun determineDessertToShow(){
        for (dessert in dessertList) {
            if (uiState.value.dessertSold >= dessert.startProductionAmount) {
                _uiState.update { currentState ->
                    currentState.copy(
                        currentDessert = dessert,
                        revenue = currentState.revenue,
                        dessertSold = currentState.dessertSold,
                        currentDessertIndex = dessertList.indexOf(dessert)
                    )
                }
                dessertList.indexOf(dessert)
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }
    }
}