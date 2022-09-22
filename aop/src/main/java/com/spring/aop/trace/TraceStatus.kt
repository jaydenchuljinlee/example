package com.spring.aop.trace

import java.time.Instant

class TraceStatus(
    val traceId: TraceId,
    val startTime: Instant,
    val message: String
) {
}