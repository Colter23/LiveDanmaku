package top.colter.live.danmaku.event.bilibili

import top.colter.live.danmaku.data.bilibili.SuperChatData
import top.colter.live.danmaku.event.AbstractEvent

class SuperChatEvent(
    val data: SuperChatData, source: String
) : AbstractEvent(source)