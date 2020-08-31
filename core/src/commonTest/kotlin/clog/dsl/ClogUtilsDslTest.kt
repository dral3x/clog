package clog.dsl

import clog.Clog
import clog.ClogProfile
import clog.api.ClogFilter
import clog.test.impl.clogProfileTest
import co.touchlab.stately.concurrency.AtomicReference
import co.touchlab.stately.concurrency.value
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ClogUtilsDslTest {

    @Test
    fun testClogFormat() {
        val formatted = Clog.format("message 1={}, '2'={}, true={}", 1, "2", true)
        assertEquals("message 1=1, '2'=2, true=true", formatted)
    }

    @Test
    fun testClogShouldLog_false() {
        val tagProvidedToFilter = AtomicReference<String?>(null)
        val priorityProvidedToFilter = AtomicReference<Clog.Priority?>(null)

        val profile = ClogProfile(
            filter = object : ClogFilter {
                override fun shouldLog(priority: Clog.Priority, tag: String?): Boolean {
                    priorityProvidedToFilter.value = priority
                    tagProvidedToFilter.value = tag

                    return false
                }
            }
        )

        clogProfileTest(profile) {
            val shouldLog = Clog.shouldLog(Clog.Priority.VERBOSE, "test tag")
            assertEquals("test tag", tagProvidedToFilter.value)
            assertEquals(Clog.Priority.VERBOSE, priorityProvidedToFilter.value)
            assertFalse(shouldLog)
        }
    }

    @Test
    fun testClogShouldLog_true() {
        val tagProvidedToFilter = AtomicReference<String?>(null)
        val priorityProvidedToFilter = AtomicReference<Clog.Priority?>(null)

        val profile = ClogProfile(
            filter = object : ClogFilter {
                override fun shouldLog(priority: Clog.Priority, tag: String?): Boolean {
                    priorityProvidedToFilter.value = priority
                    tagProvidedToFilter.value = tag

                    return true
                }
            }
        )

        clogProfileTest(profile) {
            val shouldLog = Clog.shouldLog(Clog.Priority.VERBOSE, "test tag")
            assertEquals("test tag", tagProvidedToFilter.value)
            assertEquals(Clog.Priority.VERBOSE, priorityProvidedToFilter.value)
            assertTrue(shouldLog)
        }
    }
}
