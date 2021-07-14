package com.example.androiddevchallenge.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.PhotoInfo
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.ui.theme.elevated
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding

/**
 * @author Kingsley Adio
 * @since 02 Mar, 2021
 */
@Composable
fun PuppyList(puppies: List<Puppy>, onClick: (Puppy) -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val backgroundColor = MaterialTheme.colors.primarySurface.elevated(4.dp)
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(backgroundColor)
            ) {
                TopAppBar(
                    backgroundColor = Color.Transparent,
                    contentColor = contentColorFor(backgroundColor),
                    elevation = 0.dp,
                    title = {
                        Icon(imageVector = Icons.Outlined.Pets, contentDescription = "Puppies")
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(text = "Compose Puppy")
                    },
                    modifier = Modifier.statusBarsPadding()
                )
            }
        }
    ) {
        val navHeight = with(LocalDensity.current) {
            LocalWindowInsets.current.navigationBars.bottom.toDp()
        }
        LazyColumn(
            contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, navHeight),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(puppies) { puppy ->
                PuppyItem(
                    puppy,
                    modifier = Modifier
                        .clickable { onClick(puppy) }
                )
            }
        }
    }
}

@Composable
fun PuppyItem(puppy: Puppy, modifier: Modifier = Modifier) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(Modifier.wrapContentSize()) {
                val imagePainter = rememberCoilPainter(
                    request = puppy.photos[0].small
                )
                Image(
                    painter = imagePainter,
                    contentDescription = puppy.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(84.dp)
                        .border(4.dp, puppy.genderBgColor, CircleShape)
                        .padding(2.dp)
                        .clip(CircleShape)
                )
                if (imagePainter.loadState is ImageLoadState.Loading) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
            ) {
                Text(text = puppy.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.body1)
                Text(
                    text = puppy.breed,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
                Text(
                    text = "${puppy.age} ${puppy.gender}",
                    color = puppy.genderColor,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .background(puppy.genderBgColor, RoundedCornerShape(8.dp))
                        .padding(vertical = 4.dp, horizontal = 12.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

val photoUrl = "https://dl5zpyw5k3jeb.cloudfront.net/photos/pets/50687636/1/?bust=1614516620&width=300"
val fakePuppy = Puppy(
    id = 0,
    url = "https://hello-world",
    breed = "Rotweiler",
    color = "White",
    age = "Young",
    gender = "Female",
    size = "Medium",
    coat = "Fur",
    name = "Buddie",
    description = "Lorem ipsum".repeat(20),
    listOf(PhotoInfo(photoUrl, photoUrl, photoUrl)),
    emptyList(),
    1.7f
)

@Preview
@Composable
fun Puppies() {
    PuppyList(puppies = (1..10).map { fakePuppy }, onClick = { /*TODO*/ })
}

@Preview
@Composable
fun OnePuppy() {
    PuppyItem(puppy = fakePuppy)
}
