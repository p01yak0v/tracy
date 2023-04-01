package io.polyakov.tracy.android.firebase

import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.Trace as FirebaseTrace
import io.polyakov.tracy.destination.TraceDestination
import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.model.TraceAttribute

class FirebaseDestination(
    private val firebasePerformance: FirebasePerformance
) : TraceDestination {

    private val activeTraces = mutableMapOf<String, FirebaseTrace>()

    override fun start(trace: Trace) {
        val firebaseTrace = firebasePerformance.newTrace(trace.descriptor.name)
        firebaseTrace.start()

        activeTraces[trace.descriptor.name] = firebaseTrace
    }

    override fun stop(trace: Trace) {
        val firebaseTrace = activeTraces.remove(trace.descriptor.name)
        firebaseTrace?.stop()
    }

    override fun cancel(trace: Trace) {
        // a trace will be GCed
        activeTraces.remove(trace.descriptor.name)
    }

    override fun fillAttributes(trace: Trace, attrs: List<TraceAttribute<*>>) {
        val firebaseTrace = activeTraces[trace.descriptor.name]

        for (attr in attrs) {
            when(attr) {
                is TraceAttribute.StringAttribute -> {
                    firebaseTrace?.putAttribute(attr.name.formatForFirebase(), attr.value)
                }
                is TraceAttribute.LongAttribute -> {
                    firebaseTrace?.putMetric(attr.name.formatForFirebase(), attr.value)
                }
                is TraceAttribute.FloatAttribute -> Unit // not supported
            }
        }
    }

    private fun String.formatForFirebase(): String {
        return replace("\\s|-".toRegex(), "_")
    }
}