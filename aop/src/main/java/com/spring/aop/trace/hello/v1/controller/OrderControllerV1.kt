package com.spring.aop.trace.hello.v1.controller

import com.spring.aop.trace.hello.v1.HelloTraceV1
import com.spring.aop.trace.hello.v1.service.OrderServiceV1
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequiredArgsConstructor
@RestController
class OrderControllerV1(
    private val orderServiceV1: OrderServiceV1,
    private val trace: HelloTraceV1
) {
    @GetMapping
    fun request(@RequestParam(required = false) itemId: String): String {
        val status = this.trace.begin("OrderControllerV1.request")

        try {
            this.orderServiceV1.orderItem(itemId)
            this.trace.end(status)

            return "ok"
        } catch (exception: Exception) {
            this.trace.exception(status, exception)
            throw exception
        }
    }
}