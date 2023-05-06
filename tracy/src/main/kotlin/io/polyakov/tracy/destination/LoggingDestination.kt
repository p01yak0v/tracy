package io.polyakov.tracy.destination

import io.polyakov.tracy.attribute.TraceAttribute
import io.polyakov.tracy.model.Trace

class LoggingDestination(private val tag: String) : TraceDestination {

    override fun start(trace: Trace) {
        println("[$tag] Trace ${trace.descriptor.name} is started.")
    }

    override fun stop(trace: Trace) {
        println("[$tag] Trace ${trace.descriptor.name} is stopped. Duration: ${trace.duration}")
    }

    override fun cancel(trace: Trace) {
        println("[$tag] Trace ${trace.descriptor.name} is cancelled.")
    }

    override fun fillAttributes(trace: Trace, attrs: List<TraceAttribute<*>>) {
        for (a in attrs) {
            println("[$tag] ${a.name}=${a.value}")
        }
    }
}
