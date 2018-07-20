package org.openbroker.cloudevents

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CloudEventTest {
    @Test
    fun testCloudEventSerialization() {
        val event = CloudEvent<String>(
            eventType = "io.klira.VoiceEvent",
            eventTypeVersion = "1.0",
            cloudEventsVersion = "0.1",
            source = "/station/jupiter/DiscoveryOne/HAL9000",
            eventID = "AAABYyvMcXysEwAHAAAAAA",
            eventTime = "2001-06-19T14:28:36Z",
            schemaURL = "https://klira.io/someURL",
            contentType = "text/plain",
            extensions = mapOf(
                "authorized" to true,
                "authorizationLevel" to Int.MAX_VALUE
            ),
            data = "I’m sorry, Dave. I’m afraid I can’t do that."
        )

        val json: String = jsonString(event)
        val deserialized: CloudEvent<String> = cloudEvent<String>(json)

        assertEquals(event, deserialized)
    }
}