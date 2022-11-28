package com.study.domain

import com.study.configuration.DelayQueueCreation.Companion.DELAYED_QUEUE
import com.study.functions.delayDuration
import com.study.functions.toRequestEvent
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.rabbitmq.Receiver

@Service
class Consumer(private val receiver: Receiver) {

    companion object {
        private val LOG = LoggerFactory.getLogger(Consumer::class.java)

    }

    @PostConstruct
    fun init() {
        consumerAndLogMessage()
    }

    fun consumerAndLogMessage() {
        receiver.consumeAutoAck(DELAYED_QUEUE)
            .doOnNext {
                val event = it.body.toRequestEvent()
                LOG.info("Message on ${event.delayQueue} took:${event.delayDuration()} seconds to be consumed")
            }.subscribe()
    }
}