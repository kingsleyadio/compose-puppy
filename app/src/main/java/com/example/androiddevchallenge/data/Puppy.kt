package com.example.androiddevchallenge.data

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * @author Kingsley Adio
 * @since 28 Feb, 2021
 */

class Puppy(
    val id: Int,
    val url: String,
    val breed: String,
    val color: String,
    val age: String,
    val gender: String,
    val size: String,
    val coat: String,
    val name: String,
    val description: String,
    val photos: List<PhotoInfo>,
    val tags: List<String>,
    val distance: Float
) {

    val genderColor: Color
        @Composable get() = when (gender.lowercase()) {
            "male" -> MaterialTheme.colors.secondaryVariant
            else -> MaterialTheme.colors.primaryVariant
        }

    val genderBgColor: Color
        @Composable get() = when (gender.lowercase()) {
            "male" -> MaterialTheme.colors.secondary
            else -> MaterialTheme.colors.primary
        }.copy(alpha = 0.2f)
}

class PhotoInfo(
    val small: String,
    val medium: String,
    val large: String
)
