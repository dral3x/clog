package clog

import clog.impl.DefaultFilter
import clog.impl.DefaultMessageFormatter
import clog.impl.DefaultTagProvider
import clog.impl.JsConsoleLogger
import clog.impl.clogTest
import kotlin.test.Test
import kotlin.test.assertEquals

class JsClogSingletonTest {
    @Test
    fun testDefaultConfiguration() {
        with(Clog.getInstance()) {
            assertEquals(JsConsoleLogger::class, logger::class)
            assertEquals(DefaultTagProvider::class, tagProvider::class)
            assertEquals(DefaultFilter::class, filter::class)
            assertEquals(DefaultMessageFormatter::class, messageFormatter::class)
        }
    }

    @Test
    fun testBasicLog() {
        clogTest { logger ->
            Clog.v("m1")

            assertEquals(Clog.Priority.VERBOSE, logger.lastMessagePriority)
            assertEquals(null, logger.lastMessageTag)
            assertEquals("m1", logger.lastMessage)
        }
    }
}
