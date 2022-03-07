package top.colter.live.danmaku.ws

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.Job

interface WebSocketClient {

    var client: HttpClient

    abstract fun getWebSocketUrl(): String

    abstract suspend fun DefaultClientWebSocketSession.authHandle()

    abstract fun frameHandle(frame: Frame)

    abstract fun connect(roomId: String): Job

    abstract fun disconnect(roomId: String)

}