package top.colter.live.danmaku

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import top.colter.live.danmaku.data.SiteEnum
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
                logger.info(("${event.source} -- ${event.data.user.uname}: ${event.data.content}"))
            }
        }

        class EnterRoomListener : Listener<EnterRoomEvent> {
            override suspend fun onMessage(event: EnterRoomEvent) {
                logger.info(("${event.source} -- ${event.data.uname} 进入直播间"))
            }
        }

        DanmuListener().register()
        EnterRoomListener().register()

        LiveDanmaku.connect(SiteEnum.BiliBili, "4767523")
        LiveDanmaku.connect(SiteEnum.BiliBili, "23105590")
        LiveDanmaku.join()

    }


    @Test
    fun eventTest(): Unit = runBlocking {

        class TestEvent(
            val data: String, source: String
        ) : AbstractEvent(source)

        class TestListener : Listener<TestEvent> {
            override suspend fun onMessage(event: TestEvent) {
                println("DanmuListener==In==${event.data}")
                delay(5000)
                println("DanmuListener==Out==${event.data}")
            }
        }

        TestListener().register()

        println("per-broadcast")
        TestEvent("WWWW", "").broadcast()
        println("www-broadcast")
        TestEvent("AAAAA", "").broadcast()
        println("aaa-broadcast")

        delay(10000)
    }

}