package top.colter.live.danmaku.event.bilibili

import top.colter.live.danmaku.data.bilibili.InteractData
import top.colter.live.danmaku.event.AbstractEvent

class FollowLiveEvent(
    val data: InteractData
) : AbstractEvent()