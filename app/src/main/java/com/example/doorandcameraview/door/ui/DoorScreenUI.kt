package com.example.doorandcameraview.door.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doorandcameraview.camera.ui.CameraScreenUI
import com.example.doorandcameraview.camera.ui.CameraStateUI
import com.example.doorandcameraview.camera.ui.CameraVM
import com.example.doorandcameraview.camera.ui.component.CardCameraUI
import com.example.doorandcameraview.door.ui.component.CardDoorUI
import com.example.doorandcameraview.ui.component.DataIsEmpty
import com.example.doorandcameraview.ui.component.DataIsError
import com.example.doorandcameraview.ui.component.DataIsLoading


@Composable
fun DoorScreenUI(vm: DoorVM = viewModel()) {

    val state = vm.state.collectAsState().value
    DoorScreenUI(stateUI = state)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DoorScreenUI(stateUI: DoorStateUI) {
    when (stateUI) {
        DoorStateUI.Empty -> {
            DataIsEmpty()
        }

        is DoorStateUI.Error -> {
            DataIsError(text = stateUI.error)
        }

        DoorStateUI.Loading -> {
            DataIsLoading()
        }

        is DoorStateUI.Success -> {
            LazyColumn(contentPadding = PaddingValues(horizontal = 21.dp)) {
                items(items = stateUI.data.doors) { door ->
                    CardDoorUI(door)
                }

            }
        }

    }
}