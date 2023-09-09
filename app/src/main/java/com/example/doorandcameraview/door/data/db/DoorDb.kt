package com.example.doorandcameraview.door.data.db

import com.example.doorandcameraview.door.data.model.Door
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class DoorDb:RealmObject {
    @PrimaryKey
    var id: Int=0
    var name: String=""
    var room: String?=null
    var favorites: Boolean=false
    var snapshot:String?=null
}


fun DoorDb.toDoor()= Door(id, name, room, favorites, snapshot)