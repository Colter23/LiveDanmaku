package top.colter.live.danmaku.data.bilibili

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class InteractData(
    /**
     * 消息类型 1为进场，2为关注
     */
    @SerialName("msg_type")
    val msgType: Int,
    /**
     * 	粉丝勋章
     */
    @SerialName("fans_medal")
    val fansMedal: FansMedal,
    /**
     * 	房间号
     */
    @SerialName("roomid")
    val roomId: Long,
    /**
     * 时间戳
     */
    @SerialName("timestamp")
    val timestamp: Long,
    /**
     * 触发时间
     */
    @SerialName("trigger_time")
    val triggerTime: Long,
    /**
     * 用户ID
     */
    @SerialName("uid")
    val uid: Long,
    /**
     * 用户名称
     */
    @SerialName("uname")
    val uname: String,
    /**
     * 用户颜色
     */
    @SerialName("uname_color")
    val unameColor: String
)

@Serializable
data class FansMedal(
    /**
     * 粉丝牌直播房间好
     */
    @SerialName("anchor_roomid")
    val anchorRoomId: Long,
    /**
     * 大航海等级
     */
    @SerialName("guard_level")
    val guardLevel: Int,
    /**
     * 粉丝牌等级
     */
    @SerialName("medal_level")
    val medalLevel: Int,
    /**
     * 粉丝牌名称
     */
    @SerialName("medal_name")
    val medalName: String,
    /**
     * 粉丝牌up主ID
     */
    @SerialName("target_id")
    val targetId: Long,

    )