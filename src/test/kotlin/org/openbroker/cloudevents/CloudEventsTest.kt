package org.openbroker.cloudevents

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File

class CloudEventsTest {
    @Test
    fun testCloudEventSerialization() {
        val event = CloudEvent(
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
        val deserialized: CloudEvent<String> = cloudEvent(json)

        assertEquals(event, deserialized)
    }

    @Test
    fun testCloudEventSerializationFailOnUnknownPropertyInCloudEventMetaData() {
        val json = loadJson("CloudEventWithUnknownProperty")
        assertThrows<UnrecognizedPropertyException> {
            cloudEvent<String>(json, jacksonObjectMapper())
        }
    }

    @Test
    fun testCloudEventSerializationDoNotFailOnUnknownPropertyInCloudEventData() {
        val json = loadJson("CloudEventWithUnknownPropertyInPayload")
        val deserialized: CloudEvent<ComplexClass> = cloudEvent(json)
        assertEquals(1, deserialized.data!!.a)
        assertEquals(2, deserialized.data!!.b)
    }
}

private const val JSON_PATH = "src/test/resources"

private fun loadJson(file: String): String {
    val completeFile = File("$JSON_PATH/$file.json")
    require(completeFile.exists()) { "File cannot be found: $file.json" }
    return completeFile.readText()
}
