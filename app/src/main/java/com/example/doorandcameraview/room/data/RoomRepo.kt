package com.example.doorandcameraview.room.data

import com.example.doorandcameraview.App
import com.example.doorandcameraview.api.AppApi
import com.example.doorandcameraview.room.data.db.RoomDb
import com.example.doorandcameraview.utils.DataStatus
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomRepo(private val realm: Realm = App.realm) {


    suspend fun saveTypeRoom(rooms: List<String>): DataStatus<Nothing, String> {
        return try {
            withContext(Dispatchers.IO) {
                realm.write {
                    rooms.forEach { room ->
                        this.copyToRealm(RoomDb().apply {
                            name = room
                        }, updatePolicy = UpdatePolicy.ALL)
                    }
                }
            }
            DataStatus.Success<Nothing, String>(null)
        } catch (e: Exception) {
            DataStatus.Error<Nothing, String>("${e.message}")
        }
    }
}