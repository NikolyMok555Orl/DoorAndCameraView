package com.example.doorandcameraview.api

@kotlinx.serialization.Serializable
data class ResponseJs<T, E>(
    val success: Boolean,
    val data: T?,
    val error: E?=null,
)