package com.study.configuration

import com.rabbitmq.client.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono
import reactor.rabbitmq.RabbitFlux
import reactor.rabbitmq.ReceiverOptions
import reactor.rabbitmq.SenderOptions

@Configuration
class RabbitConfiguration(private val rabbitMQProperties: RabbitMQProperties) {

    fun connectionMono() =
        Mono.fromCallable {
            ConnectionFactory().apply {
                this.host = rabbitMQProperties.host
                this.port = rabbitMQProperties.port
                this.username = rabbitMQProperties.username
                this.password = rabbitMQProperties.password
                this.virtualHost = rabbitMQProperties.virtualHost
            }.newConnection("delay-message-rabbit")
        }.onErrorMap { ex -> RuntimeException(ex.message) }
            .cache()

    @Bean
    fun sender() =
        RabbitFlux.createSender(SenderOptions().connectionMono(connectionMono()))

    @Bean
    fun receiver() =
        RabbitFlux.createReceiver(ReceiverOptions().connectionMono(connectionMono()))
}
