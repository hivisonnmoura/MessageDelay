package com.study.domain.request

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.io.Serializable
import java.time.LocalDateTime

data class RequestEvent(
    val message: String,
    val delayQueue: String,
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val enqueuedAt: LocalDateTime? = LocalDateTime.now()
) : Serializable
