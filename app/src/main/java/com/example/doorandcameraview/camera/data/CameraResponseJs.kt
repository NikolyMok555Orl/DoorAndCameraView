package com.example.doorandcameraview.camera.data

import kotlinx.serialization.Serializable

@Serializable
data class CameraResponseJs(
    val room:List<String>,
    val cameras:List<CameraJs>
)