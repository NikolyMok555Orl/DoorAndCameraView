package com.example.doorandcameraview.camera.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.doorandcameraview.R
import com.example.doorandcameraview.camera.data.model.Camera
import com.example.doorandcameraview.ui.theme.DoorAndCameraViewTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardCameraUI(camera: Camera, modifier: Modifier = Modifier) {

    val swipeableState = rememberSwipeableState(0)
    val widthSize = with(LocalDensity.current) { 60.dp.toPx() }
    val anchors = mapOf(0f to 0, -widthSize to 1)

    Box(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.7f) },
                orientation = Orientation.Horizontal
            )
    ) {


        IconButton(
            onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 10.dp)
                .size(36.dp)
                .border(
                    BorderStroke(1.dp, Color.LightGray),CircleShape
                )
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = if (camera.favorites)
                    ImageVector.vectorResource(R.drawable.ic_star_fill)
                else ImageVector.vectorResource(R.drawable.ic_star_empty),
                contentDescription = null,
                tint = Color(0xFFE0BE35)
            )
        }


        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 279.dp)
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = camera.snapshot,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Spacer(
                            modifier = Modifier
                                .padding(end = 2.dp)
                                .clip(CircleShape)
                                .size(2.dp)
                                .background(Color.Red)
                        )
                        Text(
                            text = "REC",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.Red,
                                fontSize = 8.sp
                            )
                        )
                    }
                    if (camera.favorites) {
                        IconButton(
                            onClick = { }, modifier = Modifier
                                .padding(8.dp)
                                .width(24.dp)
                                .height(24.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_star_fill),
                                contentDescription = "favority", tint = Color(0xFFE0BE35),
                                modifier = Modifier.padding(0.dp)
                            )
                        }
                    }
                }

                IconButton(
                    onClick = { }, modifier = Modifier
                        .align(Alignment.Center)
                        .size(60.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_play),
                        contentDescription = null,
                        tint = Color.White,

                        )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Text(text = camera.name, style = MaterialTheme.typography.bodyMedium)
                if (camera.rec) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_protect),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardCameraUIPreview() {
    val camera = Camera("Камера 1", "", "", 0, true, true)
    DoorAndCameraViewTheme {
        CardCameraUI(camera)
    }
}