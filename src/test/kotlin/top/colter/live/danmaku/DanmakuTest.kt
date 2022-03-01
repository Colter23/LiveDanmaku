package top.colter.live.danmaku

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import top.colter.live.danmaku.event.*
import top.colter.live.danmaku.event.bilibili.DanmuMessageEvent
import top.colter.live.danmaku.event.bilibili.EnterRoomEvent
import top.colter.live.danmaku.utils.logger
import top.colter.live.danmaku.ws.bilibili.BiliBiliWebSocketClient
import kotlin.test.Test

internal class DanmakuTest {

    @Test
    fun wsTest(): Unit = runBlocking {

        class DanmuListener : Listener<DanmuMessageEvent> {
            override suspend fun onMessage(event: DanmuMessageEvent) {
                logger.info(("${event.data.user.uname}: ${event.data.content}"))
            }
        }

        class EnterRoomListener : Listener<EnterRoomEvent> {
            override suspend fun onMessage(event: EnterRoomEvent) {
                logger.info(("${event.data.uname} 进入直播间"))
            }
        }

        DanmuListener().register()
        EnterRoomListener().register()

        val client = BiliBiliWebSocketClient()
        client.connect("21811136")
    }


    @Test
    fun eventTest(): Unit = runBlocking {

        class TestEvent(
            val data: String
        ) : AbstractEvent()

        class TestListener : Listener<TestEvent> {
            override suspend fun onMessage(event: TestEvent) {
                println("DanmuListener==In==${event.data}")
                delay(5000)
                println("DanmuListener==Out==${event.data}")
            }
        }

        TestListener().register()

        println("per-broadcast")
        TestEvent("WWWW").broadcast()
        println("www-broadcast")
        TestEvent("AAAAA").broadcast()
        println("aaa-broadcast")

        delay(10000)
    }

}