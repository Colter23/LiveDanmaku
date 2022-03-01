package top.colter.live.danmaku.handle.bilibili

import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import top.colter.live.danmaku.data.bilibili.*
import top.colter.live.danmaku.event.bilibili.DanmuMessageEvent
import top.colter.live.danmaku.event.bilibili.EnterRoomEvent
import top.colter.live.danmaku.event.bilibili.FollowLiveEvent
import top.colter.live.danmaku.event.broadcast
import top.colter.live.danmaku.utils.decode
import top.colter.live.danmaku.utils.logger

fun BiliBiliDanmakuData.messageHandle() {

//    logger.info("====${cmd}====")
//    logger.info(data.toString())
//    logger.info(info.toString())

    when (cmd) {
        // 进入/关注直播间
        "INTERACT_WORD" -> interactWord()
        // 房管?进入房间
        "WELCOME_GUARD" -> welcomeGuard()
        // 进场特效  欢迎舰长
        "ENTRY_EFFECT" -> entryEffect()
        // 老爷进入房间
        "WELCOME" -> welcome()


        // 弹幕信息
        "DANMU_MSG" -> danmuMsg()

        // SC
        "SUPER_CHAT_MESSAGE" -> superChatMessage()
        "SUPER_CHAT_MESSAGE_JPN" -> superChatMessageJpn()


        // 投喂礼物
        "SEND_GIFT" -> sendGift()
        // 礼物连击
        "COMBO_SEND" -> comboSend()
        // 购买大航海
        "GUARD_BUY" -> guardBug()
        // 续费了大航海?
        "USER_TOAST_MSG" -> userToastMsg()


        // 直播准备中
        "PREPARING" -> preparing()
        // 直播开始
        "LIVE" -> live()


        // 房间排名更新
        "ROOM_RANK" -> roomRank()
        // 小时榜更新
        "ACTIVITY_BANNER_UPDATE_V2" -> activityBannerUpdateV2()
        // 系统通知（全频道广播）
        "NOTICE_MSG" -> noticeMsg()
    }
}


private fun BiliBiliDanmakuData.interactWord() {
    val data = data?.decode<InteractData>()
    if (data?.msgType == 1) {
        EnterRoomEvent(data).broadcast()
    } else if (data?.msgType == 2) {
        FollowLiveEvent(data).broadcast()
    }
}

private fun BiliBiliDanmakuData.welcomeGuard() {

}

private fun BiliBiliDanmakuData.entryEffect() {

}

private fun BiliBiliDanmakuData.welcome() {

}

private fun BiliBiliDanmakuData.danmuMsg() {
    val info = info!!

    val danmuInfoData = info[0].jsonArray
    val content = info[1].toString().removePrefix("\"").removeSuffix("\"")
    val userData = info[2].jsonArray
    val fansMedalData = info[3].jsonArray

    val emoticonData = danmuInfoData[13].toString()
    val emoticon = if (emoticonData != "\"{}\"") {
        emoticonData.decode<Emoticon>()
    } else {
        null
    }

    val danmuInfo = DanmuInfo(
        danmuInfoData[0].jsonPrimitive.int,
        danmuInfoData[2].jsonPrimitive.int,
        danmuInfoData[3].jsonPrimitive.int,
        danmuInfoData[4].jsonPrimitive.long,
        emoticon
    )
    val user = User(
        userData[0].jsonPrimitive.long,
        userData[1].toString(),
        userData[2].jsonPrimitive.int,
        userData[7].toString()
    )
    val fansMedal = if (fansMedalData.isNotEmpty()) FansMedal(
        fansMedalData[3].jsonPrimitive.long,
        fansMedalData[6].jsonPrimitive.int,
        fansMedalData[0].jsonPrimitive.int,
        fansMedalData[1].toString(),
        fansMedalData[12].jsonPrimitive.long
    ) else {
        null
    }

    val danmaku = DanmuData(danmuInfo, content, user, fansMedal)
    logger.debug(danmaku.toString())
    DanmuMessageEvent(danmaku).broadcast()

}

private fun BiliBiliDanmakuData.superChatMessage() {

}

private fun BiliBiliDanmakuData.superChatMessageJpn() {

}

private fun BiliBiliDanmakuData.sendGift() {

}

private fun BiliBiliDanmakuData.comboSend() {

}

private fun BiliBiliDanmakuData.guardBug() {

}

private fun BiliBiliDanmakuData.userToastMsg() {

}

private fun BiliBiliDanmakuData.preparing() {

}

private fun BiliBiliDanmakuData.live() {

}

private fun BiliBiliDanmakuData.roomRank() {

}

private fun BiliBiliDanmakuData.activityBannerUpdateV2() {

}

private fun BiliBiliDanmakuData.noticeMsg() {

}

