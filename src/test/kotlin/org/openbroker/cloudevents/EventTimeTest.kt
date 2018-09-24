package org.openbroker.cloudevents

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset

class EventTimeTest {

    @Test
    fun parseEventTimeFromStringWithZoneOffsetTest() {
        val timestamp = "2018-09-24T16:34:27.638+02:00"
        val instant = parseEventTime(timestamp)
        val zonedTime: OffsetDateTime = instant.atOffset(ZoneOffset.ofHours(2))
        assertEquals(24, zonedTime.dayOfMonth)
        assertEquals(16, zonedTime.hour)
        assertEquals(34, zonedTime.minute)
        assertEquals(27, zonedTime.second)
    }

    @Test
    fun parseEventTimeFromStringWithUTC() {
        val timestamp = "2018-09-24T16:34:27.638Z"
        val instant = parseEventTime(timestamp)
        val zonedTime: OffsetDateTime = instant.atOffset(ZoneOffset.UTC)
        assertEquals(24, zonedTime.dayOfMonth)
        assertEquals(16, zonedTime.hour)
        assertEquals(34, zonedTime.minute)
        assertEquals(27, zonedTime.second)
    }
}