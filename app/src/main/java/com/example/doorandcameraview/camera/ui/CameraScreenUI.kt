package com.example.doorandcameraview.camera.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doorandcameraview.camera.data.model.Camera
import com.example.doorandcameraview.camera.data.model.RoomAndCamera
import com.example.doorandcameraview.camera.ui.component.CardCameraUI
import com.example.doorandcameraview.ui.component.DataIsEmpty
import com.example.doorandcameraview.ui.component.DataIsError
import com.example.doorandcameraview.ui.component.DataIsLoading
import com.example.doorandcameraview.ui.theme.DoorAndCameraViewTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CameraScreenUI(vm: CameraVM = viewModel()) {
    val state = vm.state.collectAsState().value
    CameraScreenUI(stateUI = state){
        vm.refresh()
    }
}


@Composable
fun CameraScreenUI(stateUI: CameraStateUI, refresh:()->Unit) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(stateUI is CameraStateUI.Loading),
        onRefresh =refresh,
    ) {
        when (stateUI) {
            CameraStateUI.Empty -> {
                DataIsEmpty()
            }

            is CameraStateUI.Error -> {
                DataIsError(text = stateUI.error)
            }

            CameraStateUI.Loading -> {
                DataIsLoading()
            }

            is CameraStateUI.Success -> {
                LazyColumn(contentPadding = PaddingValues(horizontal = 21.dp)) {
                    stateUI.data.rooms.forEach { (key, camers) ->
                        if (camers.isNotEmpty()) {
                            item {
                                Text(
                                    key ?: "Комната не определенна",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontSize = 21.sp,
                                        fontWeight = FontWeight(300)
                                    ),
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                            items(camers) { camera ->
                                CardCameraUI(camera)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CameraScreenUIPreview(){

    val state=CameraStateUI.Success(RoomAndCamera(mapOf("Room 1" to listOf(Camera("Гостиная", "","Room 1", 0,true, true
    )))))

    DoorAndCameraViewTheme {
        CameraScreenUI(state,{})
    }


}