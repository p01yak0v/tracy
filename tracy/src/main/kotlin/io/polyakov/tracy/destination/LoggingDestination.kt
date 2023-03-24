package io.polyakov.tracy.destination

import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.model.TraceAttribute

class LoggingDestination(
    private val tag: String = LoggingDestination::class.java.simpleName
) : TraceDestination {

    override fun start(trace: Trace) {
        println("Trace ${trace.descriptor.name} is started.")
    }

    override fun stop(trace: Trace) {
        println("Trace ${trace.descriptor.name} is stopped. Duration: ${trace.duration}")
    }

    override fun cancel(trace: Trace) {
        println("Trace ${trace.descriptor.name} is cancelled.")
    }

    override fun fillAttributes(trace: Trace, attrs: List<TraceAttribute<*>>) {
        for(a in attrs) {
            println("${a.name}=${a.value}")
        }
    }
}