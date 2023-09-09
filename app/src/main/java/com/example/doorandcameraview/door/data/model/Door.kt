package com.example.doorandcameraview.door.data.model

data class Door(
    val id: Int,
    val name: String,
    val roomTitle: String?,
    val favorites: Boolean,
    val snapshot:String?
)
