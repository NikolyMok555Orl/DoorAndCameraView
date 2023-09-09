package com.example.doorandcameraview.camera.data

import kotlinx.serialization.Serializable

@Serializable
data class CameraJs (
    val name:String,
    val snapshot:String,
    val room:String?,
    val id:Int,
    val favorites:Boolean,
    val rec:Boolean
)