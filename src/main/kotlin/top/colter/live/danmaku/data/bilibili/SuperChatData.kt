package top.colter.live.danmaku.data.bilibili

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuperChatData(
    @SerialName("id")
    val id: Long,
    @SerialName("price")
    val price: Int,
    @SerialName("message")
    val message: String,
    @SerialName("message_trans")
    val messageTrans: String,
    @SerialName("message_font_color")
    val messageFontColor: String,
    @SerialName("time")
    val time: Int,
    @SerialName("start_time")
    val startTime: Long,
    @SerialName("end_time")
    val endTime: Long,
    @SerialName("uid")
    val uid: Long,
    @SerialName("user_info")
    val user: UserInfo,
    @SerialName("medal_info")
    val medal: FansMedal,
    @SerialName("gift")
    val gift: Gift,
//    @SerialName("background_bottom_color")
//    val backgroundBottomColor: String,
//    @SerialName("background_color")
//    val backgroundColor: String,
//    @SerialName("background_color_end")
//    val backgroundColorEnd: String,
//    @SerialName("background_color_start")
//    val backgroundColorStart: String,
//    @SerialName("background_icon")
//    val backgroundIcon: String,
//    @SerialName("background_image")
//    val backgroundImage: String,
//    @SerialName("background_price_color")
//    val backgroundPriceColor: String,
//    @SerialName("color_point")
//    val colorPoint: Double,
//    @SerialName("dmscore")
//    val dmscore: Int,
)

@Serializable
data class UserInfo(
    @SerialName("face")
    val face: String,
    @SerialName("face_frame")
    val faceFrame: String,
    @SerialName("guard_level")
    val guardLevel: Int,
    @SerialName("name_color")
    val nameColor: String,
    @SerialName("uname")
    val uname: String,
    @SerialName("user_level")
    val userLevel: Int,
)

@Serializable
data class Gift(
    @SerialName("gift_id")
    val giftId: Long,
    @SerialName("gift_name")
    val giftName: String,
    @SerialName("num")
    val num: Int,
)


