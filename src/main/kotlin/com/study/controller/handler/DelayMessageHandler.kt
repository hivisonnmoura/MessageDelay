package com.study.controller.handler

import com.study.Utils.STRING_LENGTH
import com.study.Utils.charPool
import com.study.domain.Producer
import com.study.domain.request.RequestEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import kotlin.random.Random

@Service
class DelayMessageHandler(private val producer: Producer) {

    companion object {
        private val LOG = LoggerFactory.getLogger(DelayMessageHandler::class.java)
    }

    fun delayMessage(request: ServerRequest): Mono<ServerResponse> {
        return when (request.pathVariable("delay").toInt()) {
            5 -> producer.sendTo5SecDelay(
                RequestEvent(
                    message = randomStringByKotlinRandom(),
                    delayQueue = "delay-5"
                )
            ).also { LOG.info("Sending event to delay queue 5 seconds") }

            10 -> producer.sendTo10SecDelay(
                RequestEvent(
                    message = randomStringByKotlinRandom(),
                    delayQueue = "delay-10"
                )
            ).also { LOG.info("Sending event to delay queue 10 seconds") }

            15 -> producer.sendTo15SecDelay(
                RequestEvent(
                    message = randomStringByKotlinRandom(),
                    delayQueue = "delay-15"
                )
            ).also { LOG.info("Sending event to delay queue 15 seconds") }

            30 -> producer.sendTo30SecDelay(
                RequestEvent(
                    message = randomStringByKotlinRandom(),
                    delayQueue = "delay-30"
                )
            ).also { LOG.info("Sending event to delay queue 30 seconds") }

            else -> {
                Mono.error(RuntimeException("Delay must be 5, 10, 15, 30 !!"))
            }
        }.let {
            ServerResponse.ok()
                .body(it, Void::class.java)
        }


    }

    private fun randomStringByKotlinRandom() = (1..STRING_LENGTH)
        .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
        .joinToString("")

}