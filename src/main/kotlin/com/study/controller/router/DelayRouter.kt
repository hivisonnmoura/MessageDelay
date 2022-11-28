package com.study.controller.router

import com.study.controller.handler.DelayMessageHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class DelayRouter(private val delayMessageHandler: DelayMessageHandler) {

    @Bean
    fun sendMessageToDelayQueue(): RouterFunction<ServerResponse> {
        return route(
            GET("/delay/{delay}")
        ) { delayMessageHandler.delayMessage(it) }
    }

}