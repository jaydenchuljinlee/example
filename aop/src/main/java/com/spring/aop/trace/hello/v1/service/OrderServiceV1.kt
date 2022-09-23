package com.spring.aop.trace.hello.v1.service

import com.spring.aop.trace.hello.v1.HelloTraceV1
import com.spring.aop.trace.hello.v1.repository.OrderRepositoryV1
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Service
class OrderServiceV1(
    private val orderRepositoryV1: OrderRepositoryV1,
    private val trace: HelloTraceV1
) {
    fun orderItem(itemId: String) {
        val status = this.trace.begin("OrderServiceV1.orderItem")

        try {
            this.orderRepositoryV1.save(itemId)
            trace.end(status)
        } catch(exception: Exception) {
            this.trace.exception(status, exception)
            throw exception
        }
    }
}