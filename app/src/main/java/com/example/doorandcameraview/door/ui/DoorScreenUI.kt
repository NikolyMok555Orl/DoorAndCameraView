package com.example.doorandcameraview.door.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doorandcameraview.camera.ui.CameraScreenUI
import com.example.doorandcameraview.camera.ui.CameraStateUI
import com.example.doorandcameraview.camera.ui.CameraVM
import com.example.doorandcameraview.camera.ui.component.CardCameraUI
import com.example.doorandcameraview.door.data.model.Door
import com.example.doorandcameraview.door.ui.component.CardDoorUI
import com.example.doorandcameraview.door.ui.component.EditNameDialogUI
import com.example.doorandcameraview.ui.component.DataIsEmpty
import com.example.doorandcameraview.ui.component.DataIsError
import com.example.doorandcameraview.ui.component.DataIsLoading
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun DoorScreenUI(vm: DoorVM = viewModel()) {

    val state = vm.state.collectAsState().value
    val doorEdit = remember {
        mutableStateOf<Door?>(null)
    }
    DoorScreenUI(stateUI = state, refresh = {
        vm.refresh()
    }, openEditDoor = {
        doorEdit.value = it
    })
    doorEdit.value?.let { door ->
        EditNameDialogUI(door = door, onClose = { doorEdit.value = null }, onSave = {id,name->
            doorEdit.value = null
            vm.setNameDoor(id, name)
        })
    }

}


@Composable
fun DoorScreenUI(stateUI: DoorStateUI, refresh: () -> Unit, openEditDoor: (door: Door) -> Unit) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(stateUI is DoorStateUI.Loading),
        onRefresh = refresh,
    ) {
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
                DoorSuccessScreenUI(stateUI, openEditDoor)
            }

        }
    }
}

@Composable
private fun DoorSuccessScreenUI(stateUI: DoorStateUI.Success, openEditDoor: (door: Door) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 21.dp)) {
        items(items = stateUI.data.doors) { door ->
            CardDoorUI(door, openEditDoor)
        }

    }
}