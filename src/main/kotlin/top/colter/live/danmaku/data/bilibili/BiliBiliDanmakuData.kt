package top.colter.live.danmaku.data.bilibili

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

@Serializable
data class BiliBiliDanmakuData(
    @SerialName("cmd")
    val cmd: String,
    @SerialName("room")
    var room: String? = null,
    @SerialName("data")
    val data: JsonObject? = null,
    @SerialName("info")
    val info: JsonArray? = null
)