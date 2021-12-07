package ru.jegensomme.citingvkbot.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.util.LinkedMultiValueMap

private lateinit var objectMapper: ObjectMapper

private val MAP_TYPE_REFERENCE = object : TypeReference<Map<String, String>>() {}

fun setMapper(mapper: ObjectMapper) {
    objectMapper = mapper
}

fun <T> Any.convert(clazz: Class<T>): T = objectMapper.convertValue(this, clazz)

fun Any.toParametersMap(): LinkedMultiValueMap<String, String> {
    return LinkedMultiValueMap<String, String>().apply {
        setAll(objectMapper.convertValue(this@toParametersMap, MAP_TYPE_REFERENCE))
    }
}

fun <T> writeValue(obj: T): String {
    return try {
        objectMapper.writeValueAsString(obj)
    } catch (e: JsonProcessingException) {
        throw IllegalStateException("Invalid write to JSON:\n'$obj'", e)
    }
}
