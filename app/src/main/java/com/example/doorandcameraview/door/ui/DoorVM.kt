package com.example.doorandcameraview.door.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doorandcameraview.door.data.DoorRepo
import com.example.doorandcameraview.door.data.model.DoorAll
import com.example.doorandcameraview.utils.DataStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DoorVM:ViewModel() {

    private val doorRepo: DoorRepo = DoorRepo()

    private val _state = MutableStateFlow<DoorStateUI>(DoorStateUI.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            doorRepo.getDoor(false).collect {
                mapToState(it)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.emit(DoorStateUI.Loading)
           doorRepo.getDoor(true).collect {
                mapToState(it)
            }
        }
    }


    fun setNameDoor(id:Int, name:String){
        viewModelScope.launch {
            doorRepo.setNameDoor(id, name)
            doorRepo.getDoor(false).collect {
                mapToState(it)
            }
        }
    }

    private suspend fun mapToState(data: DataStatus<DoorAll, String>) {
        when (data) {
            is DataStatus.Error -> {
                _state.emit(DoorStateUI.Error(data.error ?: ""))

            }

            is DataStatus.Loading -> {
                _state.emit(DoorStateUI.Loading)
            }

            is DataStatus.Success -> {
                data.data?.let {
                    _state.emit(DoorStateUI.Success(it))
                } ?: kotlin.run {
                    _state.emit(DoorStateUI.Empty)
                }

            }
        }


    }

}






sealed interface DoorStateUI {

    object Loading : DoorStateUI
    object Empty : DoorStateUI

    data class Error(val error: String) : DoorStateUI

    data class Success(val data: DoorAll) : DoorStateUI

}