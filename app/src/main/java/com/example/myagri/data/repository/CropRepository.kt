package com.example.myagri.data.repository

import com.example.myagri.data.models.CropSuggestion

class CropRepository {

    fun getRecommendations(
        soil: String,
        season: String,
        location: String
    ): List<CropSuggestion> {

        return when (season) {

            "Kharif" -> listOf(
                CropSuggestion(
                    "Rice",
                    "A sample Kharif crop recommendation."
                ),
                CropSuggestion(
                    "Maize",
                    "Another crop to evaluate for this season."
                )
            )

            "Rabi" -> listOf(
                CropSuggestion(
                    "Wheat",
                    "A sample Rabi crop recommendation."
                ),
                CropSuggestion(
                    "Mustard",
                    "Another crop to evaluate for this season."
                )
            )

            "Zaid" -> listOf(
                CropSuggestion(
                    "Cucumber",
                    "A sample Zaid crop recommendation."
                ),
                CropSuggestion(
                    "Watermelon",
                    "Another crop to evaluate for this season."
                )
            )

            else -> emptyList()
        }
    }
}