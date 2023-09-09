package com.example.doorandcameraview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doorandcameraview.camera.ui.CameraScreenUI
import com.example.doorandcameraview.door.ui.DoorScreenUI
import com.example.doorandcameraview.ui.theme.DoorAndCameraViewTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenUI() {

    val listMenu = remember {
        listOf("Камеры", "Двери")
    }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {

        Text(text = "Мой дом",
            style = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Center), modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp))

        val pagerState = rememberPagerState()
        Row(
            Modifier
                .fillMaxWidth(),
        ) {
            listMenu.forEachIndexed { index, menu->
                val color =
                    if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Color.LightGray
                Column(modifier = Modifier
                    .weight(1f)
                    .clickable {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }) {
                    Text(
                        text = menu,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp)
                    )
                    Divider(color=color, thickness=2.dp,
                        modifier = Modifier.fillMaxWidth()

                    )
                }
            }
        }
        HorizontalPager(pageCount = 2, state = pagerState) { page ->
            when (page) {
                0 -> {
                    CameraScreenUI()
                }

                1 -> {
                    DoorScreenUI()
                }

                else -> {

                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MainScreenUIPreview() {
    DoorAndCameraViewTheme {
        MainScreenUI()
    }
}
