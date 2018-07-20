package org.openbroker.cloudevents

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

fun <T> jsonString(e: T): String = jacksonObjectMapper().writeValueAsString(e)

inline fun <reified T: Any> cloudEvent(payload: String): CloudEvent<T> = jacksonObjectMapper().readValue(payload)