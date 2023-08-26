package io.polyakov.tracy.android.destintation

import io.polyakov.tracy.attribute.TraceAttribute
import io.polyakov.tracy.destination.TraceDestination
import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.model.name
import androidx.tracing.Trace as JetpackTrace

class SystemTraceDestination : TraceDestination {

    private companion object {
        // multiple traces at the same time with the same name are not supported yet
        private const val DEFAULT_COOKIE = 1
    }

    override fun start(trace: Trace) {
        JetpackTrace.beginAsyncSection(trace.name, DEFAULT_COOKIE)
    }

    override fun stop(trace: Trace) {
        JetpackTrace.endAsyncSection(trace.name, DEFAULT_COOKIE)
    }

    override fun cancel(trace: Trace) {
        JetpackTrace.endAsyncSection(trace.name, DEFAULT_COOKIE)
    }

    override fun fillAttributes(trace: Trace, attrs: List<TraceAttribute<*>>) = Unit
}
