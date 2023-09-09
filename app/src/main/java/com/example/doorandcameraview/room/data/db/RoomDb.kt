package com.example.doorandcameraview.room.data.db

import com.example.doorandcameraview.camera.data.db.CameraDb
import com.example.doorandcameraview.door.data.db.DoorDb
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RoomDb : RealmObject {
    @PrimaryKey
    var name:String=""
}