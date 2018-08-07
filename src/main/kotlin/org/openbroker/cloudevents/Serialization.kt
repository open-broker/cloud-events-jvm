package org.openbroker.cloudevents

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.lang.reflect.ParameterizedType

fun <T> jsonString(e: T): String = jacksonObjectMapper().writeValueAsString(e)

inline fun <reified T: Any> cloudEvent(payload: String): CloudEvent<T> = jacksonObjectMapper().readValue(payload)


@JvmOverloads
fun <T> cloudEvent(payload: String, clazz: Class<T>, mapper: ObjectMapper = jacksonObjectMapper()): CloudEvent<T> {
    val type = mapper.typeFactory.constructParametricType(CloudEvent::class.java, clazz)
    return mapper.readValue(payload, type)
}