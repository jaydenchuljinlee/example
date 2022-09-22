package com.spring.aop.trace.hello

import com.spring.aop.trace.TraceId
import com.spring.aop.trace.TraceStatus
import mu.KLogging
import org.springframework.stereotype.Component
import java.time.Instant

const val START_PREFIX = "-->"
const val COMPLETE_PREFIX = "<--"
const val EX_PREFIX = "<X-"

@Component
class HelloTraceV1 {

    companion object : KLogging()

    fun begin(message: String):  TraceStatus {
        val traceId  = TraceId()
        val traceStatus = TraceStatus(traceId, Instant.now(), message)

        logger.info("[{}] {}{}", traceId.id, addSpace(START_PREFIX, traceId.depth!!), message)

        return traceStatus
    }

    fun addSpace(prefix: String, depth: Int): String {
        val builder: StringBuilder = StringBuilder()

        for (i in 0 until depth) {
            if (i == (depth - 1)) {
                builder.append("|").append(prefix)
            } else {
                builder.append("|\t")
            }
        }

        return builder.toString()
    }
}