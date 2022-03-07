package top.colter.live.danmaku.data.bilibili

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendGiftData (
    @SerialName("action")
    val action: String,
    @SerialName("giftId")
    val giftId: Long,
    @SerialName("giftName")
    val giftName: String,
    @SerialName("giftType")
    val giftType: Int,
    @SerialName("num")
    val num: Int,
    @SerialName("price")
    val price: Int,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("uid")
    val uid: Long,
    @SerialName("uname")
    val uname: String,
    @SerialName("medal_info")
    val medal: FansMedal,

)