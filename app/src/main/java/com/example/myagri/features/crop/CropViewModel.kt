package com.example.myagri.features.crop

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import com.example.myagri.data.repository.CropRepository
import com.example.myagri.data.models.CropSuggestion

data class CropUiState(
    val selectedSoil: String = "Select soil type",
    val selectedSeason: String = "Select season",
    val selectedLocation: String = "Select location",
    val suggestions: List<CropSuggestion> = emptyList(),
    val error: String? = null
)

class CropViewModel : ViewModel() {

    private val repository = CropRepository()

    var uiState by mutableStateOf(CropUiState())
        private set

    fun selectSoil(soil: String) {
        uiState = uiState.copy(
            selectedSoil = soil,
            suggestions = emptyList(),
            error = null
        )
    }

    fun selectSeason(season: String) {
        uiState = uiState.copy(
            selectedSeason = season,
            suggestions = emptyList(),
            error = null
        )
    }

    fun selectLocation(location: String) {
        uiState = uiState.copy(
            selectedLocation = location,
            suggestions = emptyList(),
            error = null
        )
    }

    fun getSuggestion() {

        if (
            uiState.selectedSoil == "Select soil type" ||
            uiState.selectedSeason == "Select season" ||
            uiState.selectedLocation == "Select location"
        ) {
            uiState = uiState.copy(
                error = "Please select all fields."
            )
            return
        }

        val result = repository.getRecommendations(
            soil = uiState.selectedSoil,
            season = uiState.selectedSeason,
            location = uiState.selectedLocation
        )

        uiState = uiState.copy(
            suggestions = result,
            error = null
        )
    }
}