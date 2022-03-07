package top.colter.live.danmaku.event

import kotlinx.coroutines.*

object EventManger {

    val eventMap: MutableMap<String, MutableList<Listener<Event>>> = mutableMapOf()

    inline fun <reified E : Event> addListener(listener: Listener<Event>) {
        val e = E::class.simpleName?: throw Exception("无法获取事件类型")
        eventMap.getOrPut(e){ mutableListOf() }.add(listener)
    }

    @OptIn(DelicateCoroutinesApi::class)
    inline fun <reified E : Event> broadcast(event: E) {
        val e = E::class.simpleName?: throw Exception("无法获取事件类型")
        eventMap[e]?.forEach {
            GlobalScope.launch {
                it.onMessage(event)
            }
        }
    }

}

interface Listener<in E : Event> {
    suspend fun onMessage(event: E)
}

inline fun <reified E : Event> Listener<E>.register() {
    EventManger.addListener<E>(this as Listener<Event>)
}

inline fun <reified E : Event> E.broadcast() {
    EventManger.broadcast<E>(this)
}
