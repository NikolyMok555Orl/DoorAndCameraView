package com.example.doorandcameraview

import android.app.Application
import com.example.doorandcameraview.api.Api
import com.example.doorandcameraview.camera.data.db.CameraDb

import com.example.doorandcameraview.door.data.db.DoorDb
import com.example.doorandcameraview.room.data.db.RoomDb
import io.realm.kotlin.Realm

import io.realm.kotlin.RealmConfiguration

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        api = Api()
        val configuration = RealmConfiguration.create(schema = setOf(CameraDb::class, DoorDb::class, RoomDb::class))
        realm = Realm.open(configuration)

    }




    companion object {
        lateinit var api: Api
        lateinit var realm: Realm
    }
}