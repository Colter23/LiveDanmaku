package top.colter.live.danmaku.ws

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*

abstract class WebSocketClient {

    var client: HttpClient

    constructor() {
        this.client = HttpClient {
            install(WebSockets)
            BrowserUserAgent()
        }
    }

    constructor(client: HttpClient) {
        this.client = client
    }

    abstract fun getWebSocketUrl(): String

    abstract suspend fun DefaultClientWebSocketSession.authHandle()

    abstract fun frameHandle(frame: Frame)

    abstract suspend fun connect(roomId: String)

    abstract fun disconnect()

}