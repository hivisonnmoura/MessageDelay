package com.study.configuration

import com.rabbitmq.client.AMQP
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono
import reactor.rabbitmq.QueueSpecification
import reactor.rabbitmq.Sender
import java.util.concurrent.TimeUnit

@Configuration
class DelayQueueCreation(private val sender: Sender) {

    companion object {
        private val LOG = LoggerFactory.getLogger(DelayQueueCreation::class.java)

        const val DELAYED_QUEUE = "DELAYED_QUEUE"
        private val BASIC_QUEUE_ARGUMENTS =
            mapOf(
                "x-queue-type" to "quorum",
                "x-overflow" to "reject-publish",
                "x-max-in-memory-length" to 0
            )

    }

    @PostConstruct
    fun init() {
        createDLQQueue()
        listOf(5L, 10L, 15L, 30L)
            .forEach {
                createQueue(it).block()
            }
    }

    private fun createDLQQueue() {
        sender
            .declareQueue(
                QueueSpecification.queue(DELAYED_QUEUE)
                    .durable(true)
                    .arguments(BASIC_QUEUE_ARGUMENTS)
                    .autoDelete(false)
                    .exclusive(false)
            ).block()
    }

    private fun createQueue(timeSeconds: Long): Mono<AMQP.Queue.DeclareOk> {
        val queueName = "delay-$timeSeconds"
        val queueArguments = BASIC_QUEUE_ARGUMENTS + mapOf(
            "x-message-ttl" to TimeUnit.SECONDS.toMillis(timeSeconds),
            "x-dead-letter-exchange" to "",
            "x-dead-letter-routing-key" to  DELAYED_QUEUE
        )
        return sender
            .declareQueue(
                QueueSpecification.queue(queueName)
                    .durable(true)
                    .arguments(queueArguments)
                    .autoDelete(false)
                    .exclusive(false)
            )
            .doOnError { error: Throwable ->
                LOG.error(
                    "Failed to create queue [{}]", queueName, error
                )
            }
    }


}