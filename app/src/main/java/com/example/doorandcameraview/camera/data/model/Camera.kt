package com.example.doorandcameraview.camera.data.model

data class Camera(
    val name: String,
    val snapshot: String,
    val room: String?,
    val id: Int,
    val favorites: Boolean,
    val rec: Boolean
)



