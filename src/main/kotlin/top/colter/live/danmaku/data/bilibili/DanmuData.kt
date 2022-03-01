package top.colter.live.danmaku.data.bilibili

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DanmuData(
    @SerialName("danmu_info")
    val danmuInfo: DanmuInfo,
    @SerialName("content")
    val content: String,
    @SerialName("user")
    val user: User,
    @SerialName("fans_medal")
    val fansMedal: FansMedal? = null,

    )

@Serializable
data class DanmuInfo(
    @SerialName("mode")
    val mode: Int,
    @SerialName("font_size")
    val fontSize: Int,
    @SerialName("color")
    val color: Int,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("emoticon")
    val emoticon: Emoticon? = null
)

@Serializable
data class Emoticon(
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("url")
    val url: String,
)

@Serializable
data class User(
    @SerialName("uid")
    val uid: Long,
    @SerialName("uname")
    val uname: String,
    @SerialName("admin")
    val isAdmin: Int,
    @SerialName("guard")
    val guard: String,
)