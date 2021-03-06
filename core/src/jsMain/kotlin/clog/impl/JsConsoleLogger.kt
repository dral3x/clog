package clog.impl

import clog.Clog
import clog.api.ClogLogger

class JsConsoleLogger : ClogLogger {
    override fun log(priority: Clog.Priority, tag: String?, message: String) {
        PrintlnLogger
            .getDefaultLogMessage(Clog.Priority.DEFAULT, tag, message)
            .also {
                when (priority) {
                    Clog.Priority.VERBOSE -> console.log(it)
                    Clog.Priority.DEBUG -> console.log(it)
                    Clog.Priority.INFO -> console.info(it)
                    Clog.Priority.DEFAULT -> console.log(it)
                    Clog.Priority.WARNING -> console.warn(it)
                    Clog.Priority.ERROR -> console.error(it)
                    Clog.Priority.FATAL -> console.error(it)
                }
            }
    }
}
