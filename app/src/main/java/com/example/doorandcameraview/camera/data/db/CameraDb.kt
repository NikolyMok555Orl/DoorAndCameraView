package com.example.doorandcameraview.camera.data.db

import com.example.doorandcameraview.camera.data.model.Camera
import io.realm.kotlin.ext.backlinks
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject

class CameraDb: RealmObject {
    var name: String=""
    var snapshot: String=""
    var roomTitle: String?=null
    var id: Int=0
    var favorites: Boolean=false
    var rec: Boolean=false
}


fun CameraDb.toCamera()= Camera(name, snapshot, roomTitle, id, favorites, rec)