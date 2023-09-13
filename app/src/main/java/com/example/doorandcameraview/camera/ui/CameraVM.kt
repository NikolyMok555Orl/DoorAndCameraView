package com.example.doorandcameraview.camera.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doorandcameraview.camera.data.CameraRepo
import com.example.doorandcameraview.camera.data.model.RoomAndCamera
import com.example.doorandcameraview.utils.DataStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CameraVM() : ViewModel() {

    private val cameraRepo: CameraRepo = CameraRepo()

    private val _state = MutableStateFlow<CameraStateUI>(CameraStateUI.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            cameraRepo.getCamera(false).collect {
                mapToState(it)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.emit(CameraStateUI.Loading)
            cameraRepo.getCamera(true).collect {
                mapToState(it)
            }
        }
    }

    private suspend fun mapToState(data: DataStatus<RoomAndCamera, String>) {

        when (data) {
            is DataStatus.Error -> {
                _state.emit(CameraStateUI.Error(data.error ?: ""))
            }

            is DataStatus.Loading -> {
                _state.emit(CameraStateUI.Loading)
            }

            is DataStatus.Success -> {
                data.data?.let {
                    _state.emit(CameraStateUI.Success(it))
                } ?: kotlin.run {
                    _state.emit(CameraStateUI.Empty)
                }

            }
        }
    }


}


sealed interface CameraStateUI {

    object Loading : CameraStateUI
    object Empty : CameraStateUI

    data class Error(val error: String) : CameraStateUI

    data class Success(val data: RoomAndCamera) : CameraStateUI

}