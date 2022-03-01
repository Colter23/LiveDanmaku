package top.colter.live.danmaku.event

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

object EventManger {
//    CoroutineScope
//    override val coroutineContext: CoroutineContext = Dispatchers.IO + CoroutineName("EventManger")

    val map: MutableMap<String, MutableList<Listener<Event>>> = mutableMapOf()

    inline fun <reified E : Event> addListener(listener: Listener<Event>) {
        val e = E::class.simpleName
        if (map.containsKey(e)) {
            map[e]?.add(listener)
        } else {
            if (e != null) {
                map[e] = mutableListOf(listener)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    inline fun <reified E : Event> broadcast(event: E) {
        val e = E::class.simpleName
        map[e]?.forEach {
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
