package com.study.domain

import com.study.domain.request.RequestEvent
import com.study.functions.toByteArray
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toMono
import reactor.rabbitmq.OutboundMessage
import reactor.rabbitmq.Sender

@Service
class Producer(private val sender: Sender) {

    fun sendTo5SecDelay(event: RequestEvent) =
        sender.send(
            OutboundMessage("", "delay-5", event.toByteArray()).toMono()
        )

    fun sendTo10SecDelay(event: RequestEvent) =
        sender.send(
            OutboundMessage("", "delay-10", event.toByteArray()).toMono()
        )

    fun sendTo15SecDelay(event: RequestEvent) = sender.send(
        OutboundMessage("", "delay-15", event.toByteArray()).toMono()
    )

    fun sendTo30SecDelay(event: RequestEvent) = sender.send(
        OutboundMessage("", "delay-30", event.toByteArray()).toMono()
    )

}