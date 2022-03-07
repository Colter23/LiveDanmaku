package top.colter.live.danmaku.event.bilibili

import top.colter.live.danmaku.data.bilibili.SendGiftData
import top.colter.live.danmaku.event.AbstractEvent

class SendGiftEvent(
    val data: SendGiftData, source: String
): AbstractEvent(source)