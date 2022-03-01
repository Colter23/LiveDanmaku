package top.colter.live.danmaku.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
    isLenient = true
    allowStructuredMapKeys = true
}

inline fun <reified T> String.decode() = json.decodeFromString<T>(json.serializersModule.serializer(), this)
inline fun <reified T> JsonElement.decode() = json.decodeFromJsonElement<T>(json.serializersModule.serializer(), this)


