package top.colter.live.danmaku.exception

class AuthException : Exception() {
    override val message: String
        get() = "认证失败"
}