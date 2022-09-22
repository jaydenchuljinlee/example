package com.spring.aop.trace

import lombok.Getter
import java.util.*

const val FIRST_DEPTH: Int = 0

@Getter
class TraceId(
    val id: String? = UUID.randomUUID().toString().subSequence(0, 8).toString(),
    val depth: Int? = 0
) {
    fun next(): TraceId {
        return TraceId(this.id, this.depth!! + 1)
    }

    fun previos(): TraceId {
        return TraceId(this.id, this.depth!! -1 )
    }

    fun isFirstDepth(): Boolean {
        return this.depth == FIRST_DEPTH
    }
}