package com.spring.aop.trace.hello.v1.repository

import com.spring.aop.trace.hello.v1.HelloTraceV1
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Repository

@RequiredArgsConstructor
@Repository
class OrderRepositoryV1(
    private val trace: HelloTraceV1
) {
    fun save(itemId: String) {
        val status = this.trace.begin("OrderRepositoryV1.save")

        try {
            if ("ex" == itemId) {
                throw IllegalAccessException("예외 발생")
            }

            // TODO 저장 로직
            this.sleep(1_000)

            this.trace.end(status)
        } catch (exception: Exception) {
            this.trace.exception(status, exception)
            throw exception
        }
    }

    private fun sleep(millis: Int) {
        try {
            Thread.sleep(millis.toLong())
        } catch (exception: InterruptedException) {
            exception.printStackTrace()
        }
    }
}