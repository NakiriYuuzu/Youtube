package core.logging

class KermitLogger : Logger {
    override fun v(tag: String, throwable: Throwable?, message: () -> String) {
        co.touchlab.kermit.Logger.v(tag, throwable, message)
    }

    override fun d(tag: String, message: () -> String) {
        co.touchlab.kermit.Logger.d(tag, message = message)
    }

    override fun i(tag: String, message: () -> String) {
        co.touchlab.kermit.Logger.i(tag, message = message)
    }

    override fun e(tag: String, throwable: Throwable?, message: () -> String) {
        co.touchlab.kermit.Logger.e(tag, throwable, message)
    }

    override fun w(tag: String, throwable: Throwable?, message: () -> String) {
        co.touchlab.kermit.Logger.w(tag, throwable, message)
    }
}