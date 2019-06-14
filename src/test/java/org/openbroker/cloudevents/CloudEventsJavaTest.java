package org.openbroker.cloudevents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CloudEventsJavaTest {

    @Test
    void testCloudEventSerializationSimpleType() {
        CloudEvent<String> event = new CloudEvent<>(
            "io.klira.VoiceEvent",
            "1.0",
            "0.1",
            "/station/jupiter/DiscoveryOne/HAL9000",
            "AAABYyvMcXysEwAHAAAAAA",
            "2001-06-19T14:28:36Z",
            "https://klira.io/someURL",
            "text/plain",
            "I’m sorry, Dave. I’m afraid I can’t do that."
        );

        String json = SerializationKt.jsonString(event);
        CloudEvent<String> deserialized = SerializationKt.cloudEvent(json, String.class);
        assertEquals(event, deserialized);
    }

    @Test
    void testCloudEventSerializationComplexType() throws JsonProcessingException {
        CloudEvent<ComplexClass> event = new CloudEvent<>(
            "io.klira.VoiceEvent",
            "1.0",
            "0.1",
            "/station/jupiter/DiscoveryOne/HAL9000",
            "AAABYyvMcXysEwAHAAAAAA",
            "2001-06-19T14:28:36Z",
            "https://klira.io/someURL",
            "text/plain",
            new ComplexClass(0, 1)
        );

        String json = new ObjectMapper().writeValueAsString(event);
        CloudEvent<ComplexClass> deserialized = SerializationKt.cloudEvent(json, ComplexClass.class);
        assertNotNull(deserialized.getData());
        assertEquals(event, deserialized);
    }
}
