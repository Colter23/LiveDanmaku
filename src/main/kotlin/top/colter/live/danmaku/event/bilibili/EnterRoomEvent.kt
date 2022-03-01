package top.colter.live.danmaku.event.bilibili

import top.colter.live.danmaku.data.bilibili.InteractData
import top.colter.live.danmaku.event.AbstractEvent

class EnterRoomEvent(
    val data: InteractData
) : AbstractEvent()