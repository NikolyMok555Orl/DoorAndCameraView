package com.example.doorandcameraview.room.data.db

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RoomDb : RealmObject {
    @PrimaryKey
    var name:String=""
}