package com.example.androiddevchallenge.ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Puppy
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.absoluteValue

/**
 * @author Kingsley Adio
 * @since 02 Mar, 2021
 */
@Composable
fun PuppyDetails(puppy: Puppy, onBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                text = { Text(text = "Adopt me") },
                onClick = {
                    scope.launch {
                        scaffoldState
                            .snackbarHostState
                            .showSnackbar("Your request to adopt ${puppy.name} has been submitted successfully")
                        onBack()
                    }
                },
                modifier = Modifier.navigationBarsPadding()
            )
        }
    ) {

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .height(360.dp)
            ) {
                var currentPhoto by remember { mutableStateOf(puppy.photos[0]) }
                Box(Modifier.matchParentSize()) {
                    val imagePainter = rememberCoilPainter(request = currentPhoto.large, fadeIn = true)
                    Image(
                        painter = imagePainter,
                        contentDescription = puppy.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                    if (imagePainter.loadState is ImageLoadState.Loading) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart)
                ) {
                    puppy.photos.forEach { photo ->
                        val color = when (photo == currentPhoto) {
                            true -> MaterialTheme.colors.primary
                            else -> MaterialTheme.colors.surface
                        }
                        Image(
                            painter = rememberCoilPainter(request = photo.small),
                            contentDescription = puppy.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20))
                                .clickable { currentPhoto = photo }
                                .border(4.dp, color, RoundedCornerShape(20))
                                .size(48.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .offset { IntOffset(0, scrollState.value.absoluteValue.coerceAtMost(300.dp.roundToPx())) }
                        .statusBarsPadding()
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.surface.copy(alpha = ContentAlpha.medium))
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = LocalContentColor.current.copy(alpha = ContentAlpha.high)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Text(text = puppy.name, style = MaterialTheme.typography.h4)
                with(puppy) {
                    val props = listOf(
                        "breed" to breed,
                        "coat" to coat,
                        "color" to color,
                        "size" to size
                    )
                        .filter { it.second.isNotEmpty() }
                        .joinToString(" \u2022 ") { it.second.lowercase().capitalize(Locale.ROOT) + " " + it.first }
                    Text(
                        text = props, style = MaterialTheme.typography.caption,
                        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "${puppy.age} ${puppy.gender}",
                        color = puppy.genderColor,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .background(puppy.genderBgColor, RoundedCornerShape(8.dp))
                            .padding(vertical = 4.dp, horizontal = 12.dp)
                    )
                    Icon(imageVector = Icons.Filled.Place, contentDescription = "Location")
                    val formattedDistance = "%.1f".format(puppy.distance)
                    Text(text = "${formattedDistance}km away")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "About",
                    style = MaterialTheme.typography.h6
                )

                Text(
                    modifier = Modifier.padding(bottom = 64.dp),
                    text = puppy.description,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
            }
        }
    }
}
