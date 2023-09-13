package com.example.doorandcameraview.door.data

import com.example.doorandcameraview.App
import com.example.doorandcameraview.api.AppApi
import com.example.doorandcameraview.camera.data.model.RoomAndCamera
import com.example.doorandcameraview.door.data.db.DoorDb
import com.example.doorandcameraview.door.data.db.toDoor
import com.example.doorandcameraview.door.data.model.Door
import com.example.doorandcameraview.door.data.model.DoorAll
import com.example.doorandcameraview.room.data.RoomRepo
import com.example.doorandcameraview.utils.DataStatus
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext


class DoorRepo(
    private val api: AppApi = App.api,
    private val realm: Realm = App.realm,
    private val roomRepo: RoomRepo = RoomRepo(realm)
) {


    suspend fun getDoor(isStrong: Boolean) = flow<DataStatus<DoorAll, String>> {
        if (isStrong) {
            emit(refreshData().last())
        } else {
            val res = getDoorFromDB().last()
            if (res is DataStatus.Success) {
                if (!res.data?.doors.isNullOrEmpty()) {
                    emit(res)
                } else {
                    emit(refreshData().last())
                }
            } else {
                emit(res)
            }
        }
    }


   suspend fun setNameDoor(id:Int, name: String){
       realm.write {
           // fetch a frog from the realm by primary key
           val door: DoorDb? =
               this.query(DoorDb::class, "id=$0", id).first().find()
           // modify the frog's age in the write transaction to persist the new age to the realm
           door?.name = name
       }
    }




    private suspend fun refreshData() = flow<DataStatus<DoorAll, String>> {
        val res = when (val response = getCameraFromApi()) {
            is DataStatus.Success -> {
                val doors = response.data ?: emptyList()
                val rooms = doors.mapNotNull { it.room }
                val resSaveDoor = saveDoor(doors)
                val resSaveRoom = roomRepo.saveTypeRoom(rooms)

                if (resSaveDoor is DataStatus.Success && resSaveRoom is DataStatus.Success) {
                    DataStatus.Success<Nothing, String>(null)

                } else {

                    if (resSaveDoor is DataStatus.Error) {
                        DataStatus.Error<Nothing, String>(resSaveDoor.error ?: "")
                    } else if (resSaveRoom is DataStatus.Error) {
                        DataStatus.Error<Nothing, String>(resSaveRoom.error ?: "")
                    } else {
                        DataStatus.Error<Nothing, String>("Неизвестная ошибка")
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
            emit(getDoorFromDB().last())
        } else {
            emit(DataStatus.Error<DoorAll, String>(res.error ?: ""))
        }
    }

    private suspend fun getDoorFromDB() = flow<DataStatus<DoorAll, String>> {
        kotlin.runCatching {
            realm.query(DoorDb::class).find()
        }.onSuccess { doomsDb ->

            val doors = mutableListOf<Door>()
            doomsDb.forEach {
                doors.add(it.toDoor())
            }
            emit(DataStatus.Success(DoorAll(doors)))
        }.onFailure {
            emit(DataStatus.Error("${it.message}"))
        }
    }


    private suspend fun saveDoor(doorsJs: List<DoorJs>): DataStatus<Nothing, String> {
        try {
            withContext(Dispatchers.IO) {
                realm.write {
                    doorsJs.forEach { door ->
                        this.copyToRealm(DoorDb().apply {
                            id=door.id
                            name = door.name
                            room = door.room
                            favorites = door.favorites
                            snapshot = door.snapshot
                        }, updatePolicy = UpdatePolicy.ALL)
                    }
                }
            }
            return DataStatus.Success<Nothing, String>(null)
        } catch (e: Exception) {
            return DataStatus.Error<Nothing, String>("${e.message}")
        }
    }

    private suspend fun getCameraFromApi(): DataStatus<List<DoorJs>, String> {
        return try {
            val res = withContext(Dispatchers.IO) {
                api.getDoor()
            }
            DataStatus.Success(res.data)
        } catch (e: Exception) {
            DataStatus.Error("Ошибка при загрузки камер: \n${e.message}")
        }

    }

}