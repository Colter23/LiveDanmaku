package top.colter.live.danmaku.utils

import java.io.ByteArrayOutputStream
import java.util.zip.Inflater

object Zlib {
    fun decompress(data: ByteArray): ByteArray {
        var output: ByteArray
        val inflater = Inflater()
        inflater.reset()
        inflater.setInput(data)
        try {
            ByteArrayOutputStream(data.size).use { o ->
                val buf = ByteArray(1024)
                while (!inflater.finished()) {
                    val i = inflater.inflate(buf)
                    o.write(buf, 0, i)
                }
                output = o.toByteArray()
            }
        } catch (e: Exception) {
            output = data
        }
        inflater.end()
        return output
    }
}