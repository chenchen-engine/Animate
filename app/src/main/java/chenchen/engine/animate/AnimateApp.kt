package chenchen.engine.animate

import android.app.Application
import android.content.Context
import android.util.Log
import java.io.File

/**
 * @author: chenchen
 * @since: 2025/1/13 14:15
 */
class AnimateApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // 获取系统默认的异常处理器
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

        // 设置自定义的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(GlobalExceptionHandler(this, defaultHandler))
    }
}

class GlobalExceptionHandler(private val context: Context, private val defaultHandler: Thread.UncaughtExceptionHandler?) :
    Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        // 打印异常信息
        Log.e("GlobalExceptionHandler", "未捕获的异常", throwable)

        // 在这里执行自定义逻辑，例如记录日志、上传到服务器等
        handleException(throwable)

        // 如果需要让系统继续处理异常，可以调用默认的异常处理器
        defaultHandler?.uncaughtException(thread, throwable)
    }

    private fun handleException(throwable: Throwable) {
        // 示例：保存到本地日志文件
        val logFile = File(context.filesDir, "crash_log.txt")
        logFile.appendText("${throwable.message}\n${Log.getStackTraceString(throwable)}\n")
    }
}
