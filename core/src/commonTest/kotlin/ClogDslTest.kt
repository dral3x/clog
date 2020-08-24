package clog

import clog.dsl.addTagToBlacklist
import clog.dsl.addTagToWhitelist
import clog.dsl.v
import clog.impl.clogTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ClogDslTest {
    @Test
    fun testBasicLog() {
        clogTest { logger ->
            v { "m1" }

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastMessagePriority)
            assertEquals("m1", logger.lastMessage)
        }
    }

    @Test
    fun testLogWithTag() {
        clogTest { logger ->
            v("t1") { "m1" }

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastMessagePriority)
            assertEquals("t1", logger.lastMessageTag)
            assertEquals("m1", logger.lastMessage)
        }
    }

    @Test
    fun testFormattedLog() {
        clogTest { logger ->
            v { format("m1: {}", "p1") }

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastMessagePriority)
            assertEquals("m1: p1", logger.lastMessage)
        }
    }

    @Test
    fun testFormattedLogMultipleParams() {
        clogTest { logger ->
            v { format("m1: {}, {}, {}, {}", "p1", 2, true, "p4" to "p5") }

            assertTrue(logger.messageWasLogged)
            assertEquals(Clog.Priority.VERBOSE, logger.lastMessagePriority)
            assertEquals("m1: p1, 2, true, (p4, p5)", logger.lastMessage)
        }
    }

    @Test
    fun testTagBlacklisting() {
        clogTest { logger ->
            Clog.addTagToBlacklist("t1")

            v("t1") { "m1" }
            assertFalse(logger.messageWasLogged)

            v("t2") { "m2" }
            assertTrue(logger.messageWasLogged)
            assertEquals("t2", logger.lastMessageTag)
            assertEquals("m2", logger.lastMessage)
        }
    }

    @Test
    fun testTagWhitelisting() {
        clogTest { logger ->
            Clog.addTagToWhitelist("t2")

            v("t1") { "m1" }
            assertFalse(logger.messageWasLogged)

            v("t2") { "m2" }
            assertTrue(logger.messageWasLogged)
            assertEquals("t2", logger.lastMessageTag)
            assertEquals("m2", logger.lastMessage)
        }
    }
}
