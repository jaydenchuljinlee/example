package com.spring.aop.trace.hello.v1

import com.spring.aop.trace.TraceId
import com.spring.aop.trace.TraceStatus
import mu.KLogging
import org.springframework.stereotype.Component
import java.lang.Exception
import java.time.Duration
import java.time.Instant
import java.util.Objects.nonNull

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

    fun end(status: TraceStatus) {
        complete(status, null)
    }

    fun exception(status: TraceStatus, exception: Exception) {
        complete(status, exception)
    }

    fun complete(status: TraceStatus, exception: Exception?) {
        val endTime: Instant = Instant.now()
        val durationTimeMs: Long = Duration.between(status.startTime, endTime).toMillis()

        val traceId: TraceId = status.traceId

        // Error log
        if (nonNull(exception)) {
            logger.info("[{}] {}{} time={}ms ex={}",
                traceId.id, addSpace(EX_PREFIX, traceId.depth!!), status.message, durationTimeMs, exception.toString())
            return
        }

        // Normal Complete log
        logger.info("[{}] {}{} time={}ms", traceId.id, addSpace(COMPLETE_PREFIX, traceId.depth!!), status.message, durationTimeMs)
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