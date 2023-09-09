package com.example.doorandcameraview.camera.ui.component

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.doorandcameraview.R
import com.example.doorandcameraview.camera.data.model.Camera
import com.example.doorandcameraview.ui.theme.DoorAndCameraViewTheme
import java.nio.file.WatchEvent

@Composable
fun CardCameraUI(camera: Camera, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .padding(vertical = 4.dp)
            .heightIn(min = 279.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = camera.snapshot,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Spacer(
                        modifier = Modifier.padding(end=2.dp).clip(CircleShape)
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
                        onClick = { /*TODO*/ }, modifier = Modifier
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

            IconButton(onClick = { }, modifier = Modifier.align(Alignment.Center).size(60.dp)) {
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

@Preview(showBackground = true)
@Composable
private fun CardCameraUIPreview() {
    val camera = Camera("Камера 1", "", "", 0, true, true)
    DoorAndCameraViewTheme {
        CardCameraUI(camera)
    }
}