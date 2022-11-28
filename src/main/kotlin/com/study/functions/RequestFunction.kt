package com.study.functions

import com.study.Utils
import com.study.domain.request.RequestEvent
import java.time.Duration
import java.time.LocalDateTime


fun RequestEvent.toByteArray(): ByteArray = Utils.mapper.writeValueAsBytes(this)
fun ByteArray.toRequestEvent(): RequestEvent = Utils.mapper.readValue(this, RequestEvent::class.java)

fun RequestEvent.delayDuration(): Long = Duration.between(this.enqueuedAt, LocalDateTime.now()).toSeconds()

