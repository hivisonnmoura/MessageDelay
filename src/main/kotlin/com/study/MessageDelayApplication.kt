package com.study

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("com.study.configuration")
class MessageDelayApplication

fun main(args: Array<String>){
    runApplication<MessageDelayApplication>(*args)
}