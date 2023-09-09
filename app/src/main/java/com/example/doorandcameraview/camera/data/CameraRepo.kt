package com.example.doorandcameraview.camera.data

import com.example.doorandcameraview.App
import com.example.doorandcameraview.api.AppApi
import com.example.doorandcameraview.camera.data.db.CameraDb
import com.example.doorandcameraview.camera.data.db.toCamera
import com.example.doorandcameraview.camera.data.model.Camera
import com.example.doorandcameraview.camera.data.model.RoomAndCamera
import com.example.doorandcameraview.room.data.RoomRepo
import com.example.doorandcameraview.room.data.db.RoomDb
import com.example.doorandcameraview.utils.DataStatus
import io.realm.kotlin.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

class CameraRepo(private val api: AppApi = App.api, private val realm: Realm = App.realm, private val roomRepo: RoomRepo= RoomRepo(realm)) {




    suspend fun getCamera(isStrong: Boolean) = flow<DataStatus<RoomAndCamera, String>> {
        if (isStrong) {
            emit(refreshData().last())
        } else {
            val res = getCameraFromDB().last()
            if (res is DataStatus.Success) {
                if (!res.data?.rooms.isNullOrEmpty()) {
                    emit(res)
                } else {
                    emit(refreshData().last())
                }
            } else {
                emit(res)
            }
        }
    }


    private suspend fun refreshData() = flow<DataStatus<RoomAndCamera, String>> {
        val res = when (val response = getCameraFromApi()) {
            is DataStatus.Success -> {

                val resSaveRoom = roomRepo.saveTypeRoom(response.data?.room?: emptyList())
                val resSaveCamera = saveCamera(response.data?.cameras?: emptyList())
                if (resSaveCamera is DataStatus.Success && resSaveRoom is DataStatus.Success) {
                    DataStatus.Success<Nothing, String>(null)
                } else {
                    if (resSaveCamera is DataStatus.Error) {
                        DataStatus.Error<Nothing, String>(resSaveCamera.error ?: "")
                    } else if(resSaveRoom is DataStatus.Error) {
                        DataStatus.Error<Nothing, String>(resSaveRoom.error ?: "")
                    }else{
                        DataStatus.Error<Nothing, String>( "Неизвестная ошибка")
                    }
                }

            }

            is DataStatus.Error -> {
                DataStatus.Error<RoomAndCamera, String>(response.error ?: "Сообщение")
            }

            else -> {
                DataStatus.Error<RoomAndCamera, String>(response.error ?: "Сообщение")
            }
        }
        if (res is DataStatus.Success) {
            emit(getCameraFromDB().last())
        } else {
            emit(DataStatus.Error<RoomAndCamera, String>(res.error ?: ""))
        }
    }


    private suspend fun getCameraFromDB() = flow<DataStatus<RoomAndCamera, String>> {
        kotlin.runCatching {
            realm.query(RoomDb::class).find()
        }.onSuccess { roomsDb ->
            val rooms = mutableMapOf<String?, MutableList<Camera>>()
            kotlin.runCatching {
                realm.query(CameraDb::class).find()
            }.onSuccess {cameras->
                roomsDb.forEach {
                    rooms[it.name]= mutableListOf()
                }
                if (cameras.isNotEmpty()){
                    rooms[null]= mutableListOf()
                }

                cameras.forEach {
                    if(it.roomTitle!=null &&rooms.containsKey(it.roomTitle)){
                        rooms[it.roomTitle]?.add(it.toCamera())
                    }else{
                        rooms[null]?.add(it.toCamera())
                    }
                }
                emit(DataStatus.Success(RoomAndCamera(rooms)))


            }.onFailure {
                emit(DataStatus.Error("${it.message}"))

            }



        }.onFailure {
            emit(DataStatus.Error("${it.message}"))
        }
    }




    private suspend fun saveCamera(cameras:List<CameraJs>): DataStatus<Nothing, String>{
        try {
            withContext(Dispatchers.IO) {
                realm.write {
                cameras.forEach { camera ->
                        this.copyToRealm(
                                CameraDb().apply {
                                    name = camera.name
                                    snapshot = camera.snapshot
                                    roomTitle = camera.room
                                    favorites = camera.favorites
                                    rec = camera.rec
                                })
                        }
                    }
                }
            return DataStatus.Success<Nothing, String>(null)
        } catch (e: Exception) {
            return DataStatus.Error<Nothing, String>("${e.message}")
        }



    }

    private suspend fun getCameraFromApi(): DataStatus<CameraResponseJs, String> {
        return try {
            val res = withContext(Dispatchers.IO) {
                api.getCameras()
            }
            DataStatus.Success<CameraResponseJs, String>(res.data)
        } catch (e: Exception) {

            DataStatus.Error<CameraResponseJs, String>("Ошибка при загрузки камер: \n${e.message}")


        }

    }


}