package com.spring.aop.unit

import com.spring.aop.trace.hello.v1.HelloTraceV1
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class HelloTraceV1Test(
    @Autowired val trace: HelloTraceV1
) {
    @Test
    fun begin_end() {
        val status = trace.begin("hello")
        trace.end(status)
    }

    @Test
    fun begin_exception() {
        val status = trace.begin("hello")
        trace.exception(status, RuntimeException("error!"))
    }
}