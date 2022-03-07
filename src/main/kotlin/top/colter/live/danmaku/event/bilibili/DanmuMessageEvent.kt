package top.colter.live.danmaku.event.bilibili

import top.colter.live.danmaku.data.bilibili.DanmuData
import top.colter.live.danmaku.event.AbstractEvent

class DanmuMessageEvent(
    val data: DanmuData, source: String
) : AbstractEvent(source)