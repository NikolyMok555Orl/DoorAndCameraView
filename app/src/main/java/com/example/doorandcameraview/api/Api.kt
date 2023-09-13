package com.example.doorandcameraview.api

import com.example.doorandcameraview.camera.data.CameraResponseJs
import com.example.doorandcameraview.door.data.DoorJs
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class Api :AppApi{

    override suspend fun getCameras(): ResponseJs<CameraResponseJs, String?> {
        return client.get("${API_URL}/cameras/") {}.body()
    }

    override suspend fun getDoor(): ResponseJs<List<DoorJs>, String?> {
        return client.get("${API_URL}/doors/") {}.body()
    }
    companion object {
        private const val DOMAIN = "http://cars.cprogroup.ru"


        private const val API_URL = "$DOMAIN/api/rubetek/"


        private val client = HttpClient(
        ) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    },
                    contentType = ContentType.Any
                )
                install(HttpTimeout) {
                    val timeout = 60000L
                    connectTimeoutMillis = timeout
                    requestTimeoutMillis = timeout
                    socketTimeoutMillis = timeout
                }
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }
    }
}


interface AppApi {

    suspend fun getCameras(): ResponseJs<CameraResponseJs, String?>


    suspend fun getDoor(): ResponseJs<List<DoorJs>, String?>


}