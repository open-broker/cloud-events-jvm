package org.openbroker.cloudevents

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

@PublishedApi
internal val defaultMapper: ObjectMapper = jacksonObjectMapper()
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

fun <T> jsonString(e: T): String = defaultMapper.writeValueAsString(e)

inline fun <reified T: Any> cloudEvent(
    payload: String,
    mapper: ObjectMapper = defaultMapper
): CloudEvent<T> = cloudEvent(payload, T::class.java, mapper)

@JvmOverloads
fun <T> cloudEvent(
    payload: String,
    clazz: Class<T>,
    mapper: ObjectMapper = defaultMapper
): CloudEvent<T> {
    val type = mapper.typeFactory.constructParametricType(CloudEvent::class.java, clazz)
    return mapper.readValue(payload, type)
}