package com.example.doorandcameraview.utils

sealed class DataStatus<T, E>(
    val data:T?=null,
    val error: E?=null
){

    class Loading<T, E>:DataStatus<T, E>()

    class Error<T, E>(error:E):DataStatus<T, E>(error = error)

    class Success<T, E>(data: T?):DataStatus<T,E>(data = data)

}

