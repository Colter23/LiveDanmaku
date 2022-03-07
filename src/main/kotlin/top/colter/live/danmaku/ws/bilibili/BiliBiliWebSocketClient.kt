package top.colter.live.danmaku.ws.bilibili

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import top.colter.live.danmaku.data.bilibili.*
import top.colter.live.danmaku.exception.AuthException
import top.colter.live.danmaku.handle.bilibili.messageHandle
import top.colter.live.danmaku.utils.Zlib
import top.colter.live.danmaku.utils.decode
import top.colter.live.danmaku.utils.logger
import top.colter.live.danmaku.ws.WebSocketClient
import java.nio.ByteBuffer
import kotlin.coroutines.CoroutineContext


class BiliBiliWebSocketClient(
    httpClient: HttpClient
): WebSocketClient,CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + CoroutineName("BiliBiliWebSocketClient")

    override var client: HttpClient = httpClient

    private val PACKAGE_OFFSET = 0  // 包大小偏移量
    private val HEADER_OFFSET = 4   // 头部大小偏移量
    private val HEADER_SIZE: Short = 16    // 头部大小 固定16

    // 协议版本
    private val PROTOCOL_OFFSET = 6 // 协议偏移量
    private val JSON_PROTOCOL: Short = 0    // 普通包正文不使用压缩
    private val POPULAR_PROTOCOL: Short = 1 // 心跳及认证包正文不使用压缩
    private val BUFFER_PROTOCOL: Short = 2  // 普通包正文使用zlib压缩

    // 操作码
    private val OPERATION_OFFSET = 8    // 操作码偏移量
    private val HEART_BEAT_OPERATION = 2   // 心跳包
    private val HEART_BEAT_REPLY_OPERATION = 3   // 心跳包回复
    private val MESSAGE_OPERATION = 5   // 普通包
    private val AUTH_OPERATION = 7  // 认证包
    private val AUTH_REPLY_OPERATION = 8  // 认证包回复

    private val SEQUENCE_OFFSET = 12
    private val SEQUENCE_ID = 1

    private var roomId = ""

    override fun getWebSocketUrl(): String = "wss://broadcastlv.chat.bilibili.com:443/sub"

    override fun connect(roomId: String): Job = launch {
        client.wss(getWebSocketUrl()) {
            this@BiliBiliWebSocketClient.roomId = roomId
            authHandle()
            heardBeat()
            while (isActive) {
                frameHandle(incoming.receive())
            }
        }
    }


    override fun disconnect(roomId: String) {
        client.close()
    }

    /**
     * 直播间验证
     */
    override suspend fun DefaultClientWebSocketSession.authHandle() {
        val json = "{\"roomid\": $roomId}"
        send(builtPack(json, AUTH_OPERATION))
    }

    /**
     * 发送心跳
     */
    private fun DefaultClientWebSocketSession.heardBeat() = launch {
        while (isActive) {
            send(builtPack("", HEART_BEAT_OPERATION))
            logger.info("$roomId -- 发送心跳包")
            delay(30_000)
        }
    }

    /**
     * 处理帧
     */
    override fun frameHandle(frame: Frame) {
        val buffer = frame.buffer
        val data = frame.data
        val packetLength = buffer.getInt(PACKAGE_OFFSET)
        val protocol = buffer.getShort(PROTOCOL_OFFSET)
        val operation = buffer.getInt(OPERATION_OFFSET)
        val dataArr = data.copyOfRange(HEADER_SIZE.toInt(), packetLength)

        when (protocol) {
            JSON_PROTOCOL -> {
                dataArr.danmakuHandle(operation)
            }
            POPULAR_PROTOCOL -> {

            }
            BUFFER_PROTOCOL -> {
                // zlib解压
                val decompressData = Zlib.decompress(dataArr)

                if (operation == MESSAGE_OPERATION) {
                    frameHandle(Frame.Binary(true, decompressData))
                } else {
                    decompressData.danmakuHandle(operation)
                }
            }
            else -> {
                logger.info(String(dataArr, Charsets.UTF_8))
            }
        }

        // 分割帧
        if (data.size > packetLength) {
            frameHandle(Frame.Binary(true, data.copyOfRange(packetLength, data.size)))
        }

    }

    /**
     * 处理弹幕信息
     */
    private fun ByteArray.danmakuHandle(operation: Int) {
        val danmakuStr = String(this, Charsets.UTF_8)
        when (operation) {
            AUTH_REPLY_OPERATION -> {
                if (danmakuStr == "{\"code\":0}") {
                    logger.info("$roomId -- 认证成功, 已连接到直播间")
                    return
                } else {
                    throw AuthException()
                }
            }
            HEART_BEAT_REPLY_OPERATION -> {
                logger.info("$roomId -- 接收到心跳回复: $danmakuStr")
            }
            MESSAGE_OPERATION -> {
                val danmuku = danmakuStr.decode<BiliBiliDanmakuData>()
                danmuku.room = roomId
                danmuku.messageHandle()
            }
        }

    }

    /**
     * 构建发送包
     */
    private fun builtPack(body: String, action: Int): ByteArray {
        val bodyBytes = body.toByteArray(Charsets.UTF_8)
        val buf = ByteBuffer.wrap(ByteArray(bodyBytes.size + HEADER_SIZE))
        buf.putInt(bodyBytes.size + HEADER_SIZE)
        buf.putShort(HEADER_SIZE)
        buf.putShort(BUFFER_PROTOCOL)
        buf.putInt(action)
        buf.putInt(SEQUENCE_ID)
        buf.put(bodyBytes)
        return buf.array()
    }

}