package top.colter.live.danmaku

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.websocket.*
import kotlinx.coroutines.Job
import top.colter.live.danmaku.data.SiteEnum
import top.colter.live.danmaku.data.SiteEnum.*
import top.colter.live.danmaku.utils.logger
import top.colter.live.danmaku.ws.bilibili.BiliBiliWebSocketClient

object LiveDanmaku {

    private val liveJobMap: MutableMap<SiteEnum, MutableMap<String, Job>> = mutableMapOf()

    private val client: HttpClient = HttpClient {
        install(WebSockets)
        BrowserUserAgent()
    }

    fun connect(site: SiteEnum, room: String){
        if (liveJobMap[site]?.containsKey(room) == true){
            logger.warn("$room 房间已有一个连接")
            return
        }

        liveJobMap.getOrPut(site){
            mutableMapOf()
        }[room] = when(site){
            BiliBili -> BiliBiliWebSocketClient(client).connect(room)
        }
    }

    suspend fun join(){
        liveJobMap.forEach { (_, u) ->
            u.forEach{ (_, j) ->
                j.join()
            }
        }
    }

}