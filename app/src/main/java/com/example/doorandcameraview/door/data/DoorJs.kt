package com.example.doorandcameraview.door.data

import kotlinx.serialization.Serializable

@Serializable
data class DoorJs(
    val id: Int,
    val name: String,
    val room: String?,
    val favorites: Boolean,
    val snapshot:String?=null
)
