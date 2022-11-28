package com.study.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "rabbitmq")
data class RabbitMQProperties(
    var host: String = "",
    var port: Int = 0,
    var username: String = "",
    var password: String = "",
    var virtualHost: String = ""
)