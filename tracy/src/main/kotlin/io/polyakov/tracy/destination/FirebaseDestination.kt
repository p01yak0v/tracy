package io.polyakov.tracy.destination

import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.model.TraceAttribute

class FirebaseDestination : TraceDestination {

    override fun start(trace: Trace) {
        println("[Firebase] Trace ${trace.descriptor.name} is started.")
    }

    override fun stop(trace: Trace) {
        println("[Firebase] Trace ${trace.descriptor.name} is stopped. Duration: ${trace.duration}")
    }

    override fun cancel(trace: Trace) {
        println("[Firebase] Trace ${trace.descriptor.name} is cancelled.")
    }

    override fun fillAttributes(trace: Trace, attrs: List<TraceAttribute<*>>) {
        for(a in attrs) {
            val prefix = when(a) {
                is TraceAttribute.StringAttribute -> "string"
                is TraceAttribute.FloatAttribute -> "float"
                is TraceAttribute.LongAttribute -> "long"
            }
            println("[$prefix] ${a.name}=${a.value}")
        }
    }
}