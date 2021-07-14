package com.example.androiddevchallenge.ui.starter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.systemBarsPadding

/**
 * @author Kingsley Adio
 * @since 02 Mar, 2021
 */
@Composable
fun Starter(onExplore: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(32.dp)
    ) {
        Text(
            text = "Adopt a puppy",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.align(Alignment.Start)
        )
        Icon(
            imageVector = Icons.Filled.Pets,
            tint = MaterialTheme.colors.secondary,
            contentDescription = "Puppy",
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        )
        Button(
            onClick = onExplore,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text(text = "Explore")
        }
    }
}
