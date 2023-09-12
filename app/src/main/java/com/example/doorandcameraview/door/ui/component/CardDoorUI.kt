package com.example.doorandcameraview.door.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.doorandcameraview.R
import com.example.doorandcameraview.camera.data.model.Camera
import com.example.doorandcameraview.door.data.model.Door
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardDoorUI(
    door: Door,
    onEditName: (door: Door) -> Unit,
    modifier: Modifier = Modifier
) {

    val scope= rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(0)
    val widthSize = with(LocalDensity.current) { 120.dp.toPx() }
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 10.dp)
                .align(
                    Alignment.CenterEnd
                )
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        swipeableState.animateTo(0)
                    }
                    onEditName(door)
                          }, modifier = Modifier
                    .padding(end = 15.dp)
                    .size(36.dp)
                    .border(
                        BorderStroke(1.dp, Color.LightGray), CircleShape
                    )
            ) {
                Icon(
                    ImageVector.vectorResource(R.drawable.ic_edit),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .size(36.dp)
                    .border(
                        BorderStroke(1.dp, Color.LightGray), CircleShape
                    )

            ) {
                Icon(
                    ImageVector.vectorResource(R.drawable.ic_star_empty),
                    contentDescription = null,
                    tint = Color(0xFFE0BE35)
                )
            }

        }


        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 11.dp)
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
        ) {
            Column {

                if (door.snapshot != null) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = door.snapshot,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
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
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = door.name, style = MaterialTheme.typography.bodyMedium)
                        if (door.snapshot != null) {
                            Text(
                                text = "В сети",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight(300),
                                    color = Color(0xFF999999),
                                )
                            )
                        }
                    }
                    //Color(0xFF03A9F4)
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_key_lock),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )

                }
            }

        }
    }
}